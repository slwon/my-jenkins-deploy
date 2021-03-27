#!groovy​

def deploymentComplete(Map config) {
    String _processInstanceId               = config.processInstanceId ?: 'undefined'
    String _deploymentStatus                = config.deploymentStatus ?: 'undefined'
    echo "--- ${_processInstanceId}"
    echo "--- ${deploymentStatus}"
}
