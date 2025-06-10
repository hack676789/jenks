pipeline {
    agent any  

    tools {
        maven 'M3'  
        jdk 'JDK11' 
    }

    stages {
        // Étape 1: Récupération du code source
        stage('Checkout') {
            steps {
                git url: 'https://github.com/votre-utilisateur/votre-repo.git', branch: 'main'
            }
        }

        // Étape 2: Build et exécution des tests
        stage('Build & Test') {
            steps {
                sh 'mvn clean test' 
                // OU pour Gradle: sh './gradlew clean test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'  // Publie les rapports JUnit
                    // Pour Gradle: junit '**/build/test-results/test/*.xml'
                }
                failure {
                    emailext body: 'Les tests ont échoué dans le build ${BUILD_NUMBER}. Consultez: ${BUILD_URL}',
                            subject: 'ÉCHEC des tests Jenkins - ${JOB_NAME}',
                            to: 'votre-email@example.com'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests' 
                // Pour Gradle: sh './gradlew build -x test'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true  // Archive le JAR généré
            }
        }

        // Déploiement 
        stage('Deploy') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                echo 'Déploiement en cours...'
                // Exemple: Copie vers un serveur avec SSH
                // sshPublisher(
                //     publishers: [
                //         sshPublisherDesc(
                //             configName: 'mon-serveur',
                //             transfers: [
                //                 sshTransfer(
                //                     sourceFiles: 'target/*.jar',
                //                     remoteDirectory: '/opt/app'
                //                 )
                //             ]
                //         )
                //     ]
                // )
            }
        }
    }

    // Actions post-build
    post {
        success {
            echo 'Build et tests réussis!'
            // Optionnel: Notification Slack/Email
        }
        failure {
            echo 'Échec du build ou des tests.'
        }
    }
}