pipeline {
    agent any

    environment {
        PATH+EXTRA = "/usr/local/bin:/usr/bin"
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_fb88955f1a313e55a6b2b1ecd54507ccb9c7e091'
    }

    options {
        shell("/bin/bash") // Ensure compatibility with advanced shell features
    }

    stages {
                stage('Set Maven Wrapper Permissions') {
                    steps {
                        sh 'chmod +x ./mvnw'
                    }
                }
                stage('Build Project') {
                    steps {
                        sh './mvnw clean package -DskipTests'
                    }
                }
                stage('SonarQube Analysis') {
                    steps {
                        withSonarQubeEnv('SonarQube') {
                             sh './mvnw sonar:sonar -Dsonar.host.url=http://sonarqube:9000'
                        }
                    }
                }


        stage('Code Analysis with SonarQube') {
            steps {
                echo 'Running SonarQube code analysis...'
                withSonarQubeEnv('SonarQube') {
                    sh """
                    ./mvnw sonar:sonar \
                        -Dsonar.projectKey=com.eBankify.api:eBankify \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate Check') {
            steps {
                echo 'Waiting for SonarQube Quality Gate result...'
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy Application') {
            steps {
                echo 'Deploying the application...'
                sh 'java -jar target/*.jar'
            }
        }
    }

    post {
        always {
            echo 'Archiving results and artifacts...'
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: 'target/jacoco.exec'
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