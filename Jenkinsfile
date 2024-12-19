pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_fb88955f1a313e55a6b2b1ecd54507ccb9c7e091'
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    echo 'Checking out code from GitHub...'
                }
                git branch: 'main',
                    url: 'https://github.com/khalid-oukha/eBankify-Banking-System-REST-API'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Building the project using Maven...'
                }
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Unit Tests with JaCoCo Coverage') {
            steps {
                script {
                    echo 'Running unit tests with JaCoCo coverage...'
                }
                sh 'mvn test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                script {
                    echo 'Running SonarQube code analysis...'
                }
                withSonarQubeEnv('SonarQube') {
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=com.eBankify.api:eBankify \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}
                    """
                }
            }
        }

        stage('Quality Gate Check') {
            steps {
                script {
                    echo 'Waiting for SonarQube Quality Gate result...'
                }
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy Application') {
            steps {
                script {
                    echo 'Deploying the application...'
                }
                sh 'java -jar target/*.jar'
            }
        }
    }

    post {
        always {
            script {
                echo 'Archiving results and artifacts...'
            }
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: 'target/jacoco.exec'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

        success {
            script {
                echo 'Pipeline completed successfully!'
            }
        }

        failure {
            script {
                echo 'Pipeline failed. Please check the logs.'
            }
        }
    }
}
