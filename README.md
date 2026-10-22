# Calculator REST API with a CI/CD pipeline

A Spring Boot calculator REST API with an automated pipeline. Every push to `master` is built, tested, containerized, pushed to Docker Hub and deployed with Docker Compose by Jenkins.

Built for the CSE 816 Software Production Engineering mini-project by **Sarthak Maheshwari (IMT2023014)**.

- **Repository:** https://github.com/SartMa/calculator-devops
- **Docker image:** https://hub.docker.com/r/sartma/devops-calculator

## Tech stack

| Area | Tool |
|---|---|
| Application | Java 21, Spring Boot 4.1.1 (Spring Web MVC, Actuator) |
| Build and tests | Maven (wrapper included), JUnit 5, MockMvc |
| CI/CD | Jenkins (declarative `Jenkinsfile`), triggered by a GitHub webhook |
| Containers | Docker (multi-stage build), Docker Compose, Nginx reverse proxy |

## API

All endpoints use `GET`, and `a` and `b` are numbers.

| Endpoint | Returns |
|---|---|
| `/api/v1/add?a=&b=` | `a + b` |
| `/api/v1/subtract?a=&b=` | `a - b` |
| `/api/v1/multiply?a=&b=` | `a * b` |
| `/api/v1/divide?a=&b=` | `a / b` (HTTP 400 if `b = 0`) |
| `/actuator/health` | Health status (`UP` / `DOWN`) |

A successful call returns HTTP 200:
```json
{"operation":"add","a":2.0,"b":3.0,"result":5.0}
```

A missing parameter, a non-numeric parameter or division by zero returns HTTP 400 with a structured error:
```json
{"status":400,"error":"Bad Request","message":"Division by Zero"}
```

## Project structure

```
├── src/main/java/com/sarthak/calculator/
│   ├── controller/   CalculatorController (HTTP endpoints)
│   ├── services/     CalculatorService (arithmetic)
│   ├── model/        Result, ErrorResponse (JSON records)
│   └── exception/    ZeroDivideException, GlobalExceptionHandler (errors → HTTP 400)
├── src/test/java/...  unit tests (service) and MockMvc tests (controller)
├── Dockerfile         multi-stage build: JDK build stage → JRE runtime stage, non-root user
├── .dockerignore
├── docker-compose.yml app + Nginx, network, health check, restart policy
├── nginx/default.conf reverse-proxy configuration
└── Jenkinsfile        CI/CD pipeline
```

## Build, test and run locally

You need JDK 21 or newer. Maven doesn't need to be installed, because the wrapper (`./mvnw`) downloads it.

```bash
./mvnw clean package          # compile, run the tests, build target/calculator-0.0.1-SNAPSHOT.jar
./mvnw test                   # run the tests only (reports in target/surefire-reports/)
java -jar target/calculator-0.0.1-SNAPSHOT.jar   # start on http://localhost:8080
```

## Run with Docker

```bash
docker build -t calculator .
docker run --rm -p 8080:8080 calculator
```

## Run the full stack with Docker Compose

This uses the image published on Docker Hub. Nginx is the only public entry point, on host port **8081**.

```bash
docker compose up -d --wait            # starts app + nginx, waits until healthy
docker compose ps                      # app shows (healthy)
docker compose down                    # stop and remove
```

`TAG=<n> docker compose up -d` runs a specific build. The default is `latest`.

## Call the API

```bash
curl "http://localhost:8081/api/v1/add?a=2&b=3"
curl "http://localhost:8081/api/v1/subtract?a=10&b=4"
curl "http://localhost:8081/api/v1/multiply?a=6&b=7"
curl "http://localhost:8081/api/v1/divide?a=1&b=4"
curl -i "http://localhost:8081/api/v1/divide?a=1&b=0"   # HTTP 400
curl "http://localhost:8081/actuator/health"
```

When the app runs directly (without Compose), use port `8080` instead.

## CI/CD pipeline

A push to `master` on GitHub sends a webhook to Jenkins, which runs the `Jenkinsfile`:

| Stage | What it does |
|---|---|
| Checkout | Fetches the pushed commit (`checkout scm`) |
| Build | `./mvnw clean package -DskipTests` |
| Test | `./mvnw test`; publishes JUnit results. A failing test fails the build. |
| Docker Build | Builds `sartma/devops-calculator:<build number>` and `:latest` |
| Push | Logs in to Docker Hub with Jenkins-stored credentials and pushes both tags |
| Deploy | `docker compose up -d --wait`, then smoke-tests the API and health endpoint through Nginx |

After every run, Jenkins sends an email saying whether the build succeeded or failed.

### Jenkins setup

- **Plugins:** Pipeline, Git, GitHub, JUnit, Credentials Binding, Email Extension
- **Credentials:** `dockerhub-cred`, a username and password for Docker Hub (use an access token)
- **Job:** a Pipeline job using *Pipeline script from SCM*. Point it at this repository, branch `*/master`, script path `Jenkinsfile`.
- **Webhook:** GitHub → Settings → Webhooks → `https://<public-jenkins-url>/github-webhook/`, content type `application/json`, push events. A local Jenkins needs a tunnel such as ngrok to receive it.
- **Email:** configure SMTP under *Manage Jenkins → System → Extended E-mail Notification*.
- The Jenkins machine needs JDK 21 and Docker with Compose, and the `jenkins` user must be in the `docker` group.
