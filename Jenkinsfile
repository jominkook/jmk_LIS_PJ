pipeline {
    agent any // Jenkins가 사용할 노드 (에이전트)

    stages {
        stage('Checkout') {
            steps {
                // Git 저장소에서 코드 가져오기
                git 'file:///var/git/my-repo.git'
            }
        }
        stage('Build') {
            steps {
                // Maven 빌드 명령 실행
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                // Maven 테스트 실행
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            // 파이프라인 실행 후 항상 실행되는 작업
            echo 'Pipeline execution completed.'
        }
        success {
            // 빌드 성공 시 실행
            echo 'Build and test succeeded!'
        }
        failure {
            // 빌드 실패 시 실행
            echo 'Build or test failed.'
        }
    }
}