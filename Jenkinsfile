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
        stage('Verify Maven') {
            steps {
                script {
                    echo 'Verifying Maven installation...'
                }
                sh 'mvn -version'
            }
        }

        stage('Checkout Code') {
            steps {
                script {
                    echo 'Checking out code from GitHub repository...'
                }
                // Fetch the repository from GitHub
                git branch: 'main',
                    url: 'https://github.com/khalid-oukha/eBankify-Banking-System-REST-API'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Running Maven build process...'
                }
                // Run Maven clean and package commands
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Run Unit Tests with Coverage') {
            steps {
                script {
                    echo 'Running unit tests with JaCoCo coverage...'
                }
                // Run tests and generate JaCoCo code coverage
                sh './mvnw test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                script {
                    echo 'Running code analysis with SonarQube...'
                }
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server configured in Jenkins
                    sh """
                    sonar-scanner \
                        -Dsonar.projectKey=com.eBankify.api:eBankify \
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
                script {
                    echo 'Checking SonarQube quality gate...'
                }
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    echo 'Deploying the Spring Boot application...'
                }
                // Deploy the Spring Boot application
                sh 'java -jar target/*.jar'
            }
        }
    }

    post {
        always {
            script {
                echo 'Archiving artifacts and test results...'
            }
            // Archive test results and artifacts
            junit '**/target/surefire-reports/*.xml' // Test results
            jacoco execPattern: 'target/jacoco.exec' // JaCoCo report
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true // Archive the JAR
        }

        success {
            script {
                echo 'Pipeline completed successfully!'
            }
        }

        failure {
            script {
                echo 'Pipeline failed. Please check the logs for details.'
            }
        }
    }
}