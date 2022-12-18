package ioc

abstract class IoCC {
    companion object {
        internal var strategy: Strategy = ::defaultStrategy

        fun <T> resolve(key: String, vararg args: Any) :T {
            return strategy(key, args) as T
        }

        private fun defaultStrategy(key: String, args: Array<out Any>) : Any {
            if ("Ioc.SetupStrategy" == key) {
                return SetupStrategyCommand(args[0] as Strategy)
            } else {
                throw IllegalArgumentException("Unknown dependency key: ${key}!")
            }
        }
    }
}

internal typealias Strategy = (String, Array<out Any>) -> Any
