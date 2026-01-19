pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Soldat77783/spring-python-system.git'
            }
        }

        stage('Build Java Project') {
            steps {
                dir('backend-java') {
                    sh './gradlew build'  // Linux/Mac
                    // If running on Windows agent, use: bat 'gradlew.bat build'
                }
            }
        }

        stage('Build Python Project') {
            steps {
                dir('backend-python') {
                    // Use official Python 3.12 Docker image to run Python commands
                    docker.image('python:3.12').inside {
                        sh 'pip install --upgrade pip'
                        sh 'pip install -r requirements.txt'
                        // Optional: run tests, e.g., sh 'pytest'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
