pipeline {
    agent any
    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'qa', 'staging', 'prod'],
            description: 'Select environment'
        )
        choice(
            name: 'TEST_TYPE',
            choices: ['all', 'specific_class', 'specific_method'],
            description: 'Choose what to test'
        )
        choice(
            name: 'TEST_CLASS',
            choices: [
                'ALL_TESTS',
                'TC002_CreateRequisition',
                'TC003_GenerateJD',
                'TC004_Keyword',
                'TC005_HiringTeam',
                'TC006_InterviewSetup',
                'TC007_AddCandidate_AddManually',
                'TC008_AddCandidate_AddBulk',
                'TC009_SendInviteAnd_InterviewLink',
                'TC0010_EditCandidate',
                'TC0011_CreateRequisitionBulk',
                'TC0012_Compensation',
                'TC0013_AddCandidate_ExcelUpload',
                'TC0014_RequirementIDDuplicates'
            ],
            description: 'Select test class to run'
        )
        string(
            name: 'TEST_METHOD',
            defaultValue: '',
            description: 'Test method name (e.g., testValidLogin) - Required for specific_method'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'akil', url: 'https://github.com/ips-connect-git/Aitom_Testing.git',
                credentialsId: 'github-aitom-token'
            }
        }

        stage('Test') {
            steps {
                script {
                    def testCommand = "mvn clean test -Denv=${params.ENVIRONMENT}"

                    // Enhanced logic with better validation
                    if (params.TEST_TYPE == 'specific_class') {
                        if (params.TEST_CLASS && params.TEST_CLASS != 'ALL_TESTS') {
                            testCommand = "mvn test -Dtest=${params.TEST_CLASS} -Denv=${params.ENVIRONMENT}"
                            echo "🔍 Running specific class: ${params.TEST_CLASS}"
                        } else {
                            echo "🔍 Running ALL tests (ALL_TESTS selected or no class specified)"
                        }
                    }
                    else if (params.TEST_TYPE == 'specific_method') {
                        if (params.TEST_CLASS && params.TEST_CLASS != 'ALL_TESTS' && params.TEST_METHOD) {
                            testCommand = "mvn test -Dtest=${params.TEST_CLASS}#${params.TEST_METHOD} -Denv=${params.ENVIRONMENT}"
                            echo "🔍 Running specific method: ${params.TEST_CLASS}#${params.TEST_METHOD}"
                        } else {
                            error "❌ For specific method testing, you must select a test class and provide a method name"
                        }
                    }
                    else {
                        echo "🔍 Running ALL tests"
                    }

                    echo "🚀 Executing: ${testCommand}"
                    bat testCommand
                }
            }
        }

        stage('Report') {
            steps {
                publishHTML(target: [
                    reportName: 'Extent Report',
                    reportDir: 'test-output',
                    reportFiles: 'index.html'
                ])

                archiveArtifacts artifacts: 'test-output/index.html', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "📊 Test execution completed. Check the Extent Report for details."
        }

        success {
            echo '✅ All tests passed successfully!'
            emailext (
                from: "akil.pathan@orgzstack.com",
                subject: "✅ SUCCESS: ${env.JOB_NAME} Build #${env.BUILD_NUMBER}",
                body: """
                TEST EXECUTION REPORT - SUCCESS
                ================================

                PROJECT: ${env.JOB_NAME}
                BUILD: #${env.BUILD_NUMBER}
                STATUS: SUCCESS ✅
                ENVIRONMENT: ${params.ENVIRONMENT}
                TEST TYPE: ${params.TEST_TYPE}
                TEST CLASS: ${params.TEST_CLASS}
                TEST METHOD: ${params.TEST_METHOD}
                DURATION: ${currentBuild.durationString}
                TIMESTAMP: ${new Date().format('yyyy-MM-dd HH:mm:ss')}

                ACCESS LINKS:
                - Detailed Report: ${env.BUILD_URL}Extent_20Report/
                - Console Output: ${env.BUILD_URL}console
                - Build Details: ${env.BUILD_URL}
                - Download Report: ${env.BUILD_URL}artifact/test-output/index.html

                All tests passed successfully. No immediate action required.

                ---
                This is an automated message from Jenkins CI/CD.
                """,
                to: "akil.pathan@orgzstack.com"
            )
        }

        failure {
            echo '❌ Tests failed — check the Extent Report.'
            emailext (
                from: "akil.pathan@orgzstack.com",
                subject: "❌ FAILED: ${env.JOB_NAME} Build #${env.BUILD_NUMBER}",
                body: """
                TEST EXECUTION REPORT - FAILURE
                ================================

                PROJECT: ${env.JOB_NAME}
                BUILD: #${env.BUILD_NUMBER}
                STATUS: FAILED ❌
                ENVIRONMENT: ${params.ENVIRONMENT}
                TEST TYPE: ${params.TEST_TYPE}
                TEST CLASS: ${params.TEST_CLASS}
                TEST METHOD: ${params.TEST_METHOD}
                DURATION: ${currentBuild.durationString}
                TIMESTAMP: ${new Date().format('yyyy-MM-dd HH:mm:ss')}

                DEBUGGING LINKS:
                - Test Report: ${env.BUILD_URL}Extent_20Report/
                - Console Logs: ${env.BUILD_URL}console
                - Build Details: ${env.BUILD_URL}
                - Download Report: ${env.BUILD_URL}artifact/test-output/index.html

                ACTION REQUIRED:
                Please check the test report and console logs for failure details.

                ---
                This is an automated message from Jenkins CI/CD.
                """,
                to: "akil.pathan@orgzstack.com"
            )
        }

        unstable {
            echo '⚠️ Build unstable - some tests failed but build completed.'
            emailext (
                from: "akil.pathan@orgzstack.com",
                subject: "⚠️ UNSTABLE: ${env.JOB_NAME} Build #${env.BUILD_NUMBER}",
                body: """
                TEST EXECUTION REPORT - UNSTABLE
                ================================

                PROJECT: ${env.JOB_NAME}
                BUILD: #${env.BUILD_NUMBER}
                STATUS: UNSTABLE ⚠️
                ENVIRONMENT: ${params.ENVIRONMENT}
                TEST TYPE: ${params.TEST_TYPE}
                TEST CLASS: ${params.TEST_CLASS}
                TEST METHOD: ${params.TEST_METHOD}
                DURATION: ${currentBuild.durationString}
                TIMESTAMP: ${new Date().format('yyyy-MM-dd HH:mm:ss')}

                Some tests failed but the build completed. Please review the test results.

                ACCESS LINKS:
                - Test Report: ${env.BUILD_URL}Extent_20Report/
                - Console Logs: ${env.BUILD_URL}console
                - Build Details: ${env.BUILD_URL}
                - Download Report: ${env.BUILD_URL}artifact/test-output/index.html

                ---
                This is an automated message from Jenkins CI/CD.
                """,
                to: "akil.pathan@orgzstack.com"
            )
        }
    }
}