package commands

import internals.Turnable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class TurnCommandTest {
    @Mock
    private lateinit var turnable: Turnable

    private lateinit var turn: Turn

    @BeforeEach
    fun setup() {
        turn = Turn(turnable)
    }

    @Test
    fun `change velocity angle test`() {
        val direction = 2
        whenever(turnable.getDirection()).thenReturn(direction)

        assertDoesNotThrow { turn.execute() }

        verify(turnable).setVelocityAngle(eq(direction))
    }
}
