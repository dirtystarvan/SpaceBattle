package ioc

import commands.ICommand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import strategies.InitScopesCommand
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiThreadTest {
    @BeforeEach
    fun init() {
        InitScopesCommand().execute()
    }

    @Test
    fun `some test`() {
        runBlocking {
            val job1 = launch(Dispatchers.Default) {
                IoCC.resolve<ICommand>("Scopes.Current.Set",
                    IoCC.resolve("Scopes.New", IoCC.resolve("Scopes.Root")))
                    .execute()

                IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()
                assertEquals(1, IoCC.resolve("dependency"))
            }

            val job2 = launch(Dispatchers.Default) {
                IoCC.resolve<ICommand>("Scopes.Current.Set",
                    IoCC.resolve("Scopes.New", IoCC.resolve("Scopes.Root")))
                    .execute()
                IoCC.resolve<ICommand>("Ioc.Register", "dependency", {_: Array<out Any> -> 1 }).execute()
                assertEquals(1, IoCC.resolve("dependency"))
            }

            joinAll(job1, job2)
        }
    }
}
