pipeline
{
	agent {
        label 'Slave_Mac'
    }
	
	tools {
        jdk 'JDK8_Mac'
    }
	
	stages{
		stage('Checkout') {
			steps{
				echo "------------>Checkout<------------"
			}
		}
		
		stage('Compile & Unit Tests') {
			steps{
				echo "------------>>Clean<------------"
				sh './gradlew clean'

				echo "------------>Unit Tests<------------"
				sh './gradlew test'
			}
		}

		stage('Static Code Analysis')
		{
            steps{
                echo '------------>Static Code Analysis<------------'
                withSonarQubeEnv('Sonar') {
                    sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
                }
             }
        }

        stage('Build') {
            steps{
            echo "------------>Build<------------"
                //Construir sin tarea test que se ejecutó previamente
                sh './gradlew build -x test'
            }
        }


	}
	
	post {
		always {
		    echo 'This will always run'
		}
		success {
		    echo 'This will run only if successful'
		    junit 'app/build/test-results/testDebugUnitTest/*.xml'
		}
		failure {
		    echo 'This will run only if failed'
		    mail (to: 'diego.tobar@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")
		}
		unstable {
		    echo 'This will run only if the run was marked as unstable'
		}
		changed {
		    echo 'This will run only if the state of the Pipeline has changed'
		    echo 'For example, if the Pipeline was previously failing but is now successful'
		}
	}


}