#!groovy​
// vars/log.groovy

// ---------------------------------------------------
// Public Methods
// ---------------------------------------------------

// E.g. log('invocation example')
void call(String logMsg) {
    echo logMsg
}

// E.g. log('INFO','invocation example')
void call(String logType, String logMsg) {
    echo "[${logType}] ${logMsg}"
}

// E.g. log.info('invocation example')
void info(String logMsg) {
    echo "[INFO] ${logMsg}"
}

// E.g. log.warn('invocation example')
void warn(String logMsg) {
    echo "[WARN] ${logMsg}"
}

// E.g. log.error('invocation example')
void error(String logMsg) {
    sh script: "echo \'[ERROR] ${logMsg}\'", label: 'Error encountered'
}

// E.g. log.errorAndTerminate('invocation example')
void errorAndTerminate(String logMsg) {
    this.error(logMsg)
    // Alternative method to call the jenkins error() step because it collides with the error() step in this library.
    // It terminates the job
    steps.'org.jenkinsci.plugins.workflow.steps.ErrorStep'('Job terminated')
}

// E.g. log.fatal('invocation example')
void fatal(String logMsg) {
    sh script: "echo \'[FATAL] System Error: ${logMsg}. Please contact your administrator.\'", label: 'Error encountered'
}

// E.g. log.fatalAndTerminate('invocation example')
void fatalAndTerminate(String logMsg) {
    this.fatal(logMsg)
    // Alternative method to call the jenkins error() step because it collides with the error() step in this library.
    // It terminates the job
    steps.'org.jenkinsci.plugins.workflow.steps.ErrorStep'('Job terminated')
}

void stageHeader(String stageName) {
    echo "==============\n= ${stageName} \n=============="
}

// ---------------------------------------------------
// Private Methods
// ---------------------------------------------------
