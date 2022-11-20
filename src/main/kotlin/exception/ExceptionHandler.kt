package exception

import commands.ICommand
import kotlin.reflect.KClass

class ExceptionHandler {
    private val handlers = HashMap<KClass<out ICommand>, MutableMap<KClass<*>, Handler>>()

    fun setup(cmdType: KClass<out ICommand>, exType: KClass<*>, handler: Handler) {
        if (handlers.containsKey(cmdType)) {
            handlers[cmdType]?.put(exType, handler)
        } else {
            handlers[cmdType] = mutableMapOf(Pair(exType, handler))
        }
    }

    fun handle(cmd: ICommand, ex: Exception) {
        handlers[cmd::class]?.get(ex::class)?.invoke(cmd, ex)
    }
}

typealias Handler = (ICommand, Exception) -> Unit
