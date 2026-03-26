pipeline {
    agent any  // runs on any available agent

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
            post {
                success {
                    // Archive the built JAR
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Run & Smoke Test') {
            steps {
                sh '''
                    # Start the app in background
                    nohup java -jar target/*.jar > app.log 2>&1 &
                    # Wait for it to start
                    sleep 10
                    # Smoke test: check if homepage is reachable
                    curl -f http://localhost:9090 || exit 1
                '''
            }
            post {
                always {
                    // Collect logs even if build fails
                    archiveArtifacts artifacts: 'app.log', allowEmptyArchive: true
                }
            }
        }
    }

    post {
        always {
            // Clean up: stop the app
            sh 'pkill -f "java -jar target/.*\\.jar" || true'
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
