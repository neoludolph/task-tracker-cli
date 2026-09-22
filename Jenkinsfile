pipeline {
    agent any
    tools {
        maven "Maven-3.9"
    }
    stages {
        stage('Get code') {
            steps {
                checkout scm
            }
        }

        stage('Build and test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Create JAR') {
            sh 'mvn package -DskipTests'
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}