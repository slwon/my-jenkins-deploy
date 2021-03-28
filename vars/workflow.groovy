#!groovy​

// ---------------------------------------------------
// Public Methods
// ---------------------------------------------------

def deploymentComplete(Map config) {
    String _processInstanceId           = config.processInstanceId ?: 'undefined'
    String _deploymentStatus            = config.deploymentStatus ?: 'undefined'
    String _deploymentMessage           = config.deploymentMessage ?: ''
    String _messageName = (_deploymentStatus == "success") ? "deploymentSuccess" : "deploymentFailed" // Message Event name of the bpm workflow

    log.info("Sending message event to workflow engine...")
    def requestBody = """\
                            {
                            "messageName": "${_messageName}",
                            "processInstanceId": "${_processInstanceId}",
                            "resultEnabled": true,
                            "processVariables" : {
                                "deploymentStatus" : {"value" : "${_deploymentStatus}", "type": "String"}
                                "deploymentMessage" : {"value" : "${_deploymentMessage}", "type": "String"}
                                }
                            }""".stripIndent()
    this.callRestApi(
        method: "POST",
        path:"/engine-rest/message",
        requestBody: "${requestBody}"
    )
}

// ---------------------------------------------------
// Private Methods
// ---------------------------------------------------

private def callRestApi(Map config) {
    def _method                     = config.method ?: 'GET' // Default
    def _path                       = config.path ?: ''
    def _requestBody                = config.requestBody ?: ''

    def _logRequestResponseBodyFlag = true
    if (_logRequestResponseBodyFlag){
        log.info("Request:\n ${_requestBody}")
    }

    def _host = 'https://34.78.150.62:8443'
    def _response = httpRequest authentication: 'jenkins-camunda',
                    httpMode: "${_method}",
                    url: "${_host}${_path}",
                    ignoreSslErrors: true,
                    contentType: "APPLICATION_JSON",
                    requestBody: "${_requestBody}",
                    validResponseCodes : '200:299',
                    consoleLogResponseBody: _logRequestResponseBodyFlag
    return _response
}
