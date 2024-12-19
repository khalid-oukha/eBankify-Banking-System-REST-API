pipeline {
    agent any

    environment {
        // Environment variables for SonarQube
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_fb88955f1a313e55a6b2b1ecd54507ccb9c7e091'
    }

    tools {
        maven 'Maven3' // Ensure Maven is configured in Jenkins under Global Tool Configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Fetch the repository from GitHub
                git branch: 'main',
                    url: 'https://github.com/khalid-oukha/eBankify-Banking-System-REST-API'
            }
        }

        stage('Build') {
            steps {
                // Run Maven clean and package commands
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Run Unit Tests with Coverage') {
            steps {
                // Run tests and generate JaCoCo code coverage
                sh './mvnw test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server configured in Jenkins
                    sh """
                    sonar-scanner \
                        -Dsonar.projectKey=eBankify \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.junit.reportPaths=target/surefire-reports \
                        -Dsonar.jacoco.reportPaths=target/site/jacoco/jacoco.xml \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate Check') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy Application') {
            steps {
                // Deploy the Spring Boot application
                sh 'java -jar target/*.jar'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // Test results
            jacoco execPattern: 'target/jacoco.exec' // JaCoCo report
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}