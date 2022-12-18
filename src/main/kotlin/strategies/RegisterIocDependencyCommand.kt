package strategies

import commands.ICommand

class RegisterIocDependencyCommand(
    private val key: String,
    private val strategy: (Array<out Any>) -> Any
): ICommand {
    override fun execute() {
        val currentScope = ScopeBasedStrategy.currentScope.get()

        if (currentScope.dependencies.containsKey(key)) {
            throw UnsupportedOperationException("$key: this dependency already exists and cannot be redefined!")
        } else {
            currentScope.dependencies[key] = strategy
        }
    }
}
