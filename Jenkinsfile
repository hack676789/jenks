pipeline {
    agent any  

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/hack676789/jenks.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test' 
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
                failure {
                    echo 'Les tests ont échoué'
                    // Commentez l'email temporairement
                    // emailext body: 'Les tests ont échoué dans le build ${BUILD_NUMBER}. Consultez: ${BUILD_URL}',
                    //         subject: 'ÉCHEC des tests Jenkins - ${JOB_NAME}',
                    //         to: 'votre-email@example.com'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'  
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
            }
        }

        stage('Deploy') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                echo 'Déploiement en cours...'
            }
        }
    }

    post {
        success {
            echo 'Build et tests réussis!'
        }
        failure {
            echo 'Échec du build ou des tests.'
        }
    }
}