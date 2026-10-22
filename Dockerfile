FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S appuser && adduser -S appuser -G appuser
WORKDIR /work
EXPOSE 8081
COPY --from=build --chown=appuser:appuser /app/target/calculator-0.0.1-SNAPSHOT.jar app.jar
USER appuser
ENTRYPOINT ["java", "-jar", "app.jar"]
