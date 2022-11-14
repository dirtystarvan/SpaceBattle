package exception

import commands.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class ExceptionHandlerTest {
    private lateinit var queue: ArrayDeque<ICommand>
    private lateinit var exHandler: ExceptionHandler

    @Mock
    private lateinit var cmdMove: Move

    @BeforeEach
    fun init() {
        exHandler = ExceptionHandler()
        queue = ArrayDeque()
    }

    // Реализовать обработчик исключения, который ставит Команду, пишущую в лог в очередь Команд.
    @Test
    fun `setup log command test`() {
        exHandler.setup(Move::class, IllegalArgumentException::class) {
                cmd, ex -> queue.addLast(Log(cmd, ex))
        }

        whenever(cmdMove.execute()).thenThrow(IllegalArgumentException(""))
        queue.addFirst(cmdMove)
        popAndExecute()

        assertTrue(queue.isNotEmpty())
        assertEquals(Log::class, queue.last()::class)
    }

    // Реализовать обработчик исключения, который ставит в очередь Команду - повторитель команды, выбросившей исключение.
    @Test
    fun `setup retry command test`() {
        exHandler.setup(Move::class, IllegalArgumentException::class) {
                cmd, _ -> queue.addLast(RetryFirst(cmd))
        }

        whenever(cmdMove.execute()).thenThrow(IllegalArgumentException(""))
        queue.addFirst(cmdMove)
        popAndExecute()

        val testCommand = queue.last()

        assertTrue(queue.isNotEmpty())
        assertEquals(RetryFirst::class, testCommand::class)
        assertThrows<IllegalArgumentException> { testCommand.execute() }
        verify(cmdMove, times(2)).execute()
    }

    // Реализовать следующую обработку исключений:
    // при первом выбросе исключения повторить команду, при повторном выбросе исключения записать информацию в лог.
    @Test
    fun `retry then log strategy test`(@Mock logCmd: Log) {
        exHandler.setup(Move::class, IllegalArgumentException::class) {
                cmd, _ -> queue.addLast(RetryFirst(cmd))
        }

        exHandler.setup(RetryFirst::class, IllegalArgumentException::class) {
                _, _ -> queue.addLast(logCmd)
        }

        whenever(cmdMove.execute()).thenThrow(IllegalArgumentException(""))
        queue.addFirst(cmdMove)

        runExecution()

        verify(cmdMove, times(2)).execute()
        verify(logCmd, times(1)).execute()
    }

    //Реализовать стратегию обработки исключения - повторить два раза, потом записать в лог.
    @Test
    fun `retry twice then log test`(@Mock logCmd: Log) {
        exHandler.setup(Move::class, IllegalArgumentException::class) {
                cmd, _ -> queue.addLast(RetryFirst(cmd))
        }

        exHandler.setup(RetryFirst::class, IllegalArgumentException::class) {
                cmd, _ -> queue.addLast(RetrySecond(cmd))
        }

        exHandler.setup(RetrySecond::class, IllegalArgumentException::class) {
                _, _ -> queue.addLast(logCmd)
        }

        whenever(cmdMove.execute()).thenThrow(IllegalArgumentException(""))
        queue.addFirst(cmdMove)

        runExecution()

        verify(cmdMove, times(3)).execute()
        verify(logCmd, times(1)).execute()
    }

    private fun popAndExecute() {
        val cmd = queue.removeFirst()

        try {
            cmd.execute()
        } catch (ex: Exception) {
            exHandler.handle(cmd, ex)
        }
    }

    private fun runExecution() {
        while(queue.isNotEmpty()) {
            popAndExecute()
        }
    }
}
