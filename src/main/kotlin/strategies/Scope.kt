package strategies

internal class Scope(
    internal val dependencies: MutableMap<String, (Array<out Any>) -> Any>,
    private val parent: Scope?
) {
    fun resolve(key: String, args: Array<out Any>): Any? {
        return if (dependencies.containsKey(key)) {
            dependencies[key]!!.invoke(args)
        } else {
            parent?.resolve(key, args)
        }
    }
}
