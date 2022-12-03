package commands.macro

import commands.ICommand
import exception.CommandException

class SimpleMacroCommand(private val chain: List<ICommand>): ICommand {
    override fun execute() {
        chain.forEach {
            try {
                it.execute()
            } catch (ex: Exception) {
                throw CommandException("Command sequence terminated unexpectedly")
            }
        }
    }
}
