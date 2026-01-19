pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Soldat77783/spring-python-system.git'
            }
        }

        stage('Build Java Project') {
            steps {
                dir('backend-java') {
                    sh './gradlew build'  // on Linux/Mac
                    // on Windows use: bat 'gradlew.bat build'
                }
            }
        }

        stage('Build Python Project') {
            steps {
                dir('backend-python') {
                    sh 'pip install -r requirements.txt'
                    // Optional: run tests if you have any
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
