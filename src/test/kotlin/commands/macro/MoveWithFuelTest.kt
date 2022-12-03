package commands.macro

import commands.BurnFuel
import commands.CheckFuel
import commands.ICommand
import commands.Move
import internals.Fuelable
import internals.Moveable
import org.jetbrains.bio.viktor.F64Array
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class MoveWithFuelTest {
    @Mock private lateinit var fuelable: Fuelable
    @Mock private lateinit var moveable: Moveable

    private lateinit var macro: SimpleMacroCommand
    private var cmdChain = mutableListOf<ICommand>()

    @BeforeEach
    fun setup() {
        cmdChain.clear()
        macro = SimpleMacroCommand(cmdChain)
    }

    //Реализовать команду движения по прямой с расходом топлива, используя команды с предыдущих шагов
    @Test
    fun `move with fuel burning test`() {
        whenever(fuelable.getFuelLevel()).thenReturn(1)
        whenever(fuelable.getFuelConsumption()).thenReturn(1)
        whenever(moveable.getPosition()).thenReturn(F64Array.of(12.0, 5.0))
        whenever(moveable.getVelocity()).thenReturn(F64Array.of(-7.0, 3.0))

        cmdChain.add(CheckFuel(fuelable))
        cmdChain.add(Move(moveable))
        cmdChain.add(BurnFuel(fuelable))

        assertDoesNotThrow { macro.execute() }

        verify(fuelable, times(2)).getFuelLevel()
        verify(fuelable).setFuelLevel(eq(0))
        verify(moveable, times(1))
            .setPosition(org.mockito.kotlin.check {
                assertEquals(2, it.length)
                assertEquals(5.0, it[0])
                assertEquals(8.0, it[1])
            })
    }

}
