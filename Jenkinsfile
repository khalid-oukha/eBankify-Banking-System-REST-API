pipeline {
    agent any

    stages {
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

        stage('Code Analysis') {
            environment {
                scannerHome = tool 'Sonar'
            }
            steps {
                script {
                    withSonarQubeEnv('Sonar') {
                        sh "${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=com.eBankify.api:eBankify \
                            -Dsonar.projectName=com.eBankify.api:eBankify \
                            -Dsonar.projectVersion=1.0 \
                            -Dsonar.sources=. \
                            -Dsonar.host.url=http://sonarqube:9000"
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube analysis...'
                script {
                    sh """
                        ./mvnw clean verify sonar:sonar \\
                        -Dsonar.host.url=http://sonarqube:9000 \\
                        -Dsonar.login=sqa_79a3e891e56bccaa51e12b846ea824add6c4ad36
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