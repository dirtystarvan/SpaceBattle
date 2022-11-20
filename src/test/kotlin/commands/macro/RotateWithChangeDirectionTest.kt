package commands.macro

import commands.CheckMoving
import commands.ICommand
import commands.Rotate
import commands.Turn
import internals.Moveable
import internals.Turnable
import org.jetbrains.bio.viktor.F64Array
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
class RotateWithChangeDirectionTest {
    @Mock private lateinit var turnable: Turnable
    @Mock private lateinit var movable: Moveable

    private lateinit var macro: SimpleMacroCommand
    private var cmdChain = mutableListOf<ICommand>()

    @BeforeEach
    fun setup() {
        cmdChain.clear()
        macro = SimpleMacroCommand(cmdChain)
    }

    //Реализовать команду поворота, которая еще и меняет вектор мгновенной скорости, если есть.
    @Test
    fun `change direction after rotate test`() {
        val direction = 1

        whenever(turnable.getDirection()).thenReturn(direction)
        whenever(turnable.getAngularVelocity()).thenReturn(8)
        whenever(turnable.getDirectionsNumber()).thenReturn(8)
        whenever(movable.getVelocity()).thenReturn(F64Array.of(0.0, 1.0))

        cmdChain.add(Rotate(turnable))
        cmdChain.add(CheckMoving(movable))
        cmdChain.add(Turn(turnable))

        assertDoesNotThrow { macro.execute() }

        verify(turnable).setVelocityAngle(eq(direction))
    }
}
