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
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // wird immer ausgeführt: Testergebnisse von JUnit werden unter /target abgelegt -> Weist Jenkins darauf hin, diese einzulesen, um Diagramme zu generieren
        }
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true // legt .jar-Dateien im Jenkins Server ab, sofern Build erfolgreich
        }
    }
}