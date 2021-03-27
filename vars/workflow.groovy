#!groovy​

// ---------------------------------------------------
// Public Methods
// ---------------------------------------------------

def deploymentComplete(Map config) {
    String _processInstanceId               = config.processInstanceId ?: 'undefined'
    String _deploymentStatus                = config.deploymentStatus ?: 'undefined'

    def requestBody = """\
                            {
                            "messageName": "deploymentComplete",
                            "processInstanceId": "${_processInstanceId}",
                            "resultEnabled": true,
                            "processVariables" : {
                                "deploymentStatus" : {"value" : "${_deploymentStatus}", "type": "String"}
                                }
                            }""".stripIndent()

    echo "Sending message event to workflow engine..."
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

    def _method             = config.method ?: 'GET' // Default
    def _path               = config.path ?: ''
    def _requestBody        = config.requestBody ?: ''

    log.info("Request:\n ${{_requestBody}}")
    def _host = 'https://34.78.150.62:8443'
    def _response = httpRequest authentication: 'jenkins-camunda',
                    httpMode: "${_method}",
                    url: "${_host}${_path}",
                    ignoreSslErrors: true,
                    contentType: "APPLICATION_JSON",
                    requestBody: "${_requestBody}",
                    validResponseCodes : '200:299',
                    consoleLogResponseBody: true
    return _response
}
