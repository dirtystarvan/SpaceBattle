package strategies

import commands.ICommand
import ioc.IoCC

class SingleThreadScopeInitCommand: ICommand {
    override fun execute() {
        IoCC.resolve<ICommand>("Ioc.Register",
            "Scopes.Storage",
            {_: Array<out Any> -> mutableMapOf<String, (Array<out Any>) -> Any>() }
        )
    }
}
