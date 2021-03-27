#!groovy​

def deploymentComplete(Map config) {
    String _processInstanceId               = config.processInstanceId ?: 'undefined'
    String _deploymentStatus                = config.deploymentStatus ?: 'undefined'
    echo "--- ${_processInstanceId}"
    echo "--- ${_deploymentStatus}"

    def requestBody = """\
                            {
                            "messageName": "deploymentComplete",
                            "processInstanceId": "${_processInstanceId}",
                            "resultEnabled": true,
                            "processVariables" : {
                                "deploymentStatus" : {"value" : "${_deploymentStatus}", "type": "String"}
                                }
                            }""".stripIndent()
    echo "Sending Message Event to Workflow Engine\n Request:\n ${requestBody}"
    def WORKFLOW_HOST = 'https://34.78.150.62:8443'
    def response =  httpRequest authentication: 'jenkins-camunda',
                    httpMode: 'POST',
                    url: "${WORKFLOW_HOST}/engine-rest/message",
                    ignoreSslErrors: true,
                    contentType: "APPLICATION_JSON",
                    requestBody: "${requestBody}",
                    responseHandle: 'NONE',
                    validResponseCodes : '200,204',
                    consoleLogResponseBody: true
}
