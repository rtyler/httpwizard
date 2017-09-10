#!/usr/bin/env groovy

pipeline {
    agent { docker 'openjdk:8-alpine' }

    stages {
        stage('Build') {
            steps {
                sh './gradlew'
            }
        }
    }
}
