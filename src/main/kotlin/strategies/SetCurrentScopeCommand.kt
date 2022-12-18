package strategies

import commands.ICommand

internal class SetCurrentScopeCommand(private val scope: Scope): ICommand {
    override fun execute() {
        ScopeBasedStrategy.currentScope.set(scope)
    }
}
