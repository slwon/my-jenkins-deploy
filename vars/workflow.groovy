#!groovy​

def deploymentComplete(Map config) {
    String _id        = config.id ?: 'undefined'
    String _status    = config.status ?: 'undefined'
    echo "--- ${_id}"
    echo "--- ${_status}"
}
