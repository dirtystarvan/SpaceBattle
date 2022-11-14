package commands

class RetrySecond(private val cmd: ICommand): ICommand {
    override fun execute() {
        cmd.execute()
    }
}
