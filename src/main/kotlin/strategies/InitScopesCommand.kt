package strategies

import commands.ICommand
import ioc.IoCC
import java.util.concurrent.ConcurrentHashMap

internal class InitScopesCommand: ICommand {
    override fun execute() {
        if (ScopeBasedStrategy.root != null) {
            return
        }

        IoCC.resolve<ICommand>("Ioc.SetupStrategy", ScopeBasedStrategy::resolve).execute()

        val dependencies = ConcurrentHashMap<String, (Array<out Any>) -> Any>()
        val scope = Scope(dependencies, null)
        ScopeBasedStrategy.root = scope

        dependencies["Scopes.Storage"] = { ConcurrentHashMap<String, (Array<out Any>) -> Any>() }
        dependencies["Scopes.New"] = { args -> Scope(IoCC.resolve("Scopes.Storage"), args[0] as Scope) }
        dependencies["Scopes.Current"] = {
            ScopeBasedStrategy.currentScope.get() ?: ScopeBasedStrategy.defaultScope
        }
        dependencies["Scopes.Current.Set"] = { args -> SetCurrentScopeCommand(args[0] as Scope) }
        dependencies["Ioc.Register"] = { RegisterIocDependencyCommand(it[0] as String, it[1] as (Array<out Any>) -> Any) }

        SetCurrentScopeCommand(scope).execute()
    }
}
