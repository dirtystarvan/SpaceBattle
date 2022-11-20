package commands

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
class CheckFuelCommandTest {
    @Mock
    private lateinit var fuelable: Fuelable

    private lateinit var checkFuel: CheckFuel

    @BeforeEach
    fun setup() {
        checkFuel = CheckFuel(fuelable)
    }

    @Test
    fun `check fuel level success test`() {
        whenever(fuelable.getFuelLevel()).thenReturn(1)

        assertDoesNotThrow { checkFuel.execute() }
    }

    @Test
    fun `not enough fuel test`() {
        whenever(fuelable.getFuelLevel()).thenReturn(-1)

        assertThrows<CommandException> { checkFuel.execute() }
    }
}
