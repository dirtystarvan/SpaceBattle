package ioc

import commands.ICommand
import org.junit.jupiter.api.BeforeEach
import strategies.InitScopesCommand
import strategies.SingleThreadScopeInitCommand
import kotlin.test.Test
import kotlin.test.assertNotNull

class SingleThreadTest {
    @BeforeEach
    fun init() {
        InitScopesCommand().execute()

        val scope = IoCC.resolve<Any>("Scopes.New", IoCC.resolve("Scopes.Root"))
        IoCC.resolve<ICommand>("Scopes.Current.Set", scope).execute()
        SingleThreadScopeInitCommand().execute()
    }

    @Test
    fun `create new scope`() {
        assertNotNull(IoCC.resolve<Any>("Scopes.New", IoCC.resolve("Scopes.Root")))
    }

    @Test
    fun `scope storage is non concurrent map`() {
        IoCC.resolve<MutableMap<String, (Array<out Any>) -> Any>>("Scopes.Storage")
    }
}
