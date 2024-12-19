pipeline {
    agent any

    environment {
        // SonarQube environment variables
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_fb88955f1a313e55a6b2b1ecd54507ccb9c7e091'
    }

    tools {
        // Ensures Maven is installed and configured under Global Tool Configuration in Jenkins
        maven 'Maven3'
    }

    stages {
        // Checkout code from GitHub
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from GitHub...'
                git branch: 'main', url: 'https://github.com/khalid-oukha/eBankify-Banking-System-REST-API'
            }
        }

        // Verify Maven installation
        stage('Verify Maven') {
            steps {
                echo 'Verifying Maven installation...'
                sh 'mvn -version' // Verifies that Maven is properly configured and accessible
            }
        }

        // Build the project using Maven
        stage('Build') {
            steps {
                echo 'Building the project using Maven...'
                sh 'mvn clean package -DskipTests' // Clean and package the project, skipping tests
            }
        }

        // Run unit tests and collect JaCoCo coverage
        stage('Run Unit Tests with JaCoCo Coverage') {
            steps {
                echo 'Running unit tests with JaCoCo coverage...'
                sh 'mvn test' // Run unit tests and generate JaCoCo report
            }
        }

        // Run code analysis with SonarQube
        stage('Code Analysis with SonarQube') {
            steps {
                echo 'Running SonarQube code analysis...'
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server configured in Jenkins
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=com.eBankify.api:eBankify \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_AUTH_TOKEN}
                    """
                }
            }
        }

        // Wait for SonarQube Quality Gate result
        stage('Quality Gate Check') {
            steps {
                echo 'Waiting for SonarQube Quality Gate result...'
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true // Abort the pipeline if Quality Gate fails
                }
            }
        }

        // Deploy the application
        stage('Deploy Application') {
            steps {
                echo 'Deploying the application...'
                sh 'java -jar target/*.jar' // Deploy the application (run the generated JAR file)
            }
        }
    }

    // Post-actions for archiving results and handling outcomes
    post {
        always {
            echo 'Archiving results and artifacts...'
            junit '**/target/surefire-reports/*.xml' // Archive test results
            jacoco execPattern: 'target/jacoco.exec' // Archive JaCoCo code coverage report
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true // Archive the final JAR file
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Please check the logs for details.'
        }
    }
}
