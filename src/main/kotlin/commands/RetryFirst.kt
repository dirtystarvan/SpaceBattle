package commands

class RetryFirst(private val cmd: ICommand): ICommand {
    override fun execute() {
        cmd.execute()
    }
}
