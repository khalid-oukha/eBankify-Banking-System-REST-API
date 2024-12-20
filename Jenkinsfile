pipeline {
    agent any

    stages {
        stage('Debug Environment') {
                steps {
                    sh 'java -version'
                    sh 'mvn -version'
                    sh 'printenv'
                }
        }

        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/khalid-oukha/eBankify-Banking-System-REST-API'
            }
        }

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

        stage('Run Unit Tests with Coverage') {
            steps {
                echo 'Running unit tests and generating coverage reports...'
                sh './mvnw clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar -Dsonar.verbose=true'
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