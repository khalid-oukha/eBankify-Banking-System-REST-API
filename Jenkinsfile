pipeline {
    agent any

    environment {
        // Define the correct path for Maven if needed
        PATH = "/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/Maven3/bin:${env.PATH}"
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_fb88955f1a313e55a6b2b1ecd54507ccb9c7e091'
    }

    tools {
        maven 'Maven3' // Ensure Maven is configured in Jenkins under Global Tool Configuration
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

        stage('Verify Maven') {
            steps {
                script {
                    echo 'Verifying Maven installation...'
                }
                sh 'mvnww -version' 
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Building the project using Maven...'
                }
                sh 'mvnww clean package -DskipTests' // Run Maven build, skipping tests
            }
        }

        stage('Run Unit Tests with JaCoCo Coverage') {
            steps {
                script {
                    echo 'Running unit tests with JaCoCo coverage...'
                }
                sh 'mvnw test' // Run unit tests
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                script {
                    echo 'Running SonarQube code analysis...'
                }
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server configured in Jenkins
                    sh """
                    mvnw sonar:sonar \
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
                sh 'java -jar target/*.jar' // Deploy the built application
            }
        }
    }

    post {
        always {
            script {
                echo 'Archiving results and artifacts...'
            }
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
                echo 'Pipeline failed. Please check the logs.'
            }
        }
    }
}
