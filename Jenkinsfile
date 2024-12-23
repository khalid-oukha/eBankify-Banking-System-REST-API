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
    }

    post {
        always {
            echo 'Archiving results and artifacts...'
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                junit '**/target/surefire-reports/*.xml'
            }
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                jacoco execPattern: 'target/jacoco.exec'
            }
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        success {
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }

}