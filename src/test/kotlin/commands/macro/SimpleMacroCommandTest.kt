package commands.macro

import commands.BurnFuel
import commands.ICommand
import exception.CommandException
import internals.Fuelable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class SimpleMacroCommandTest {
    @Mock
    private lateinit var fuelable: Fuelable

    private lateinit var macro: SimpleMacroCommand
    private var cmdChain = mutableListOf<ICommand>()

    @BeforeEach
    fun setup() {
        cmdChain.clear()
        macro = SimpleMacroCommand(cmdChain)
    }

    @Test
    fun `macro command execute success test`() {
        whenever(fuelable.getFuelConsumption()).thenReturn(1)
        whenever(fuelable.getFuelLevel()).thenReturn(1)
        cmdChain.add(BurnFuel(fuelable))

        assertDoesNotThrow { macro.execute() }
    }

    @Test
    fun `macro command terminated with exception test`() {
        whenever(fuelable.getFuelLevel()).thenThrow(IllegalStateException())
        cmdChain.add(BurnFuel(fuelable))

        assertThrows<CommandException>("Command sequence terminated unexpectedly") { macro.execute() }

    }
}
