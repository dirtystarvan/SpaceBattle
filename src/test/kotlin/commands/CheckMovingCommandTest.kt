package commands

import exception.CommandException
import internals.Moveable
import org.jetbrains.bio.viktor.F64Array
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CheckMovingCommandTest {
    @Mock
    private lateinit var movable: Moveable

    private lateinit var checkMoving: CheckMoving

    @BeforeEach
    fun setup() {
        checkMoving = CheckMoving(movable)
    }

    @Test
    fun `object has velocity test`() {
        whenever(movable.getVelocity()).thenReturn(F64Array.of(1.0, 1.0))

        assertDoesNotThrow { checkMoving.execute() }
    }

    @Test
    fun `object does not move test`() {
        whenever(movable.getVelocity()).thenReturn(F64Array.of(0.0, 0.0))

        assertThrows<CommandException>("This object does not move!") { checkMoving.execute() }
    }
}
