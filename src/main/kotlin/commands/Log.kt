package commands

import org.slf4j.LoggerFactory

class Log(private val cmd: ICommand, private val exception: Exception): ICommand {
    companion object {
        @Suppress("JAVA_CLASS_ON_COMPANION")
        @JvmStatic
        private val logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    override fun execute() {
        logger.info("Exception during execute [${cmd::class.simpleName}] command: ${exception.message}")
    }
}
