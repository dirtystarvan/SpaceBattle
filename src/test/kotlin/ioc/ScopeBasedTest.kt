package ioc

import commands.ICommand
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import strategies.InitScopesCommand
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ScopeBasedTest {
    @BeforeEach
    fun init() {
        InitScopesCommand().execute()
    }

    @Test
    fun `root scope is available`() {
        assertNotNull(IoCC.resolve<Any>("Scopes.Root"))
    }

    @Test
    fun `create scope`() {
        assertNotNull(IoCC.resolve("Scopes.New", IoCC.resolve("Scopes.Root")))
    }

    @Test
    fun `can resolve that was registered`() {
        IoCC.resolve<ICommand>("Scopes.Current.Set",
            IoCC.resolve("Scopes.New", IoCC.resolve("Scopes.Root")))
            .execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()

        assertEquals(1, IoCC.resolve("dependency"))
    }

    @Test
    fun `once registered cannot be redefined`() {
        IoCC.resolve<ICommand>("Scopes.Current.Set",
            IoCC.resolve("Scopes.New", IoCC.resolve("Scopes.Root")))
            .execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()

        assertEquals(1, IoCC.resolve("dependency"))
        assertThrows<Exception> {
            IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 2 }).execute()
        }
    }

    @Test
    fun `resolving dependencies based on current scope`() {
        val firstScope = IoCC.resolve<Any>("Scopes.New", IoCC.resolve("Scopes.Root"))

        IoCC.resolve<ICommand>("Scopes.Current.Set", firstScope).execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()
        assertEquals(1, IoCC.resolve("dependency"))

        val secondScope = IoCC.resolve<Any>("Scopes.New", IoCC.resolve("Scopes.Root"))

        IoCC.resolve<ICommand>("Scopes.Current.Set", secondScope).execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 2 }).execute()
        assertEquals(2, IoCC.resolve("dependency"))

        IoCC.resolve<ICommand>("Scopes.Current.Set", firstScope).execute()

        assertEquals(1, IoCC.resolve("dependency"))
    }

    @Test
    fun `resolving dependencies based on scopes hierarchy`() {
        val firstScope = IoCC.resolve<Any>("Scopes.New", IoCC.resolve("Scopes.Root"))
        IoCC.resolve<ICommand>("Scopes.Current.Set", firstScope).execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()
        assertEquals(1, IoCC.resolve("dependency"))
        assertNull(IoCC.resolve("dependency2"))

        val secondScope = IoCC.resolve<Any>("Scopes.New", firstScope)
        IoCC.resolve<ICommand>("Scopes.Current.Set", secondScope).execute()

        IoCC.resolve<ICommand>("Ioc.Register", "dependency2", {_: Array<out Any> -> 1 }).execute()
        assertEquals(1, IoCC.resolve("dependency"))
        assertEquals(1, IoCC.resolve("dependency2"))
    }
}
