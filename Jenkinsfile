pipeline {
    agent any

    environment {
        IMAGE = 'sartma/devops-calculator'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build') {
            steps { sh './mvnw package -DskipTests -B' }
        }
        stage('Test') {
            steps {
                sh './mvnw test -B'
            }
             post {
                always{
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Docker Build') {
            steps { sh "docker build -t ${IMAGE}:${BUILD_NUMBER} -t ${IMAGE}:latest ." }
        }
        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]){
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${IMAGE}:${BUILD_NUMBER}"
                    sh "docker push ${IMAGE}"
                }
            }
        }
        // stage('Deploy') — add after docker-compose.yml exists
    }

    post {
        success {
            emailext(
                to: 'sarthaktechindia@gmail.com',
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build succeeded.\n\nDetails: ${env.BUILD_URL}"
            )
        }
        failure {
            emailext(
                to: 'sarthaktechindia@gmail.com',
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build failed.\n\nConsole log: ${env.BUILD_URL}console"
            )
        }
    }
}