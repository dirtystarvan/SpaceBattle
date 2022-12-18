package strategies

internal class ScopeBasedStrategy {
    companion object {
        internal var root: Scope? = null
        internal var defaultScope: () -> Any? = { root }
        internal var currentScope: ThreadLocal<Scope> = ThreadLocal()

        internal fun resolve(key: String, args: Array<Any>): Any? {
            return if ("Scopes.Root" == key) {
                root
            } else {
                var scope = currentScope.get()

                if (scope == null) {
                    scope = defaultScope() as Scope
                }

                scope.resolve(key, args)
            }
        }
    }
}
