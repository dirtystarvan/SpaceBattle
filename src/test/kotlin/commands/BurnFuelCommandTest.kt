package commands

import internals.Fuelable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class BurnFuelCommandTest {
    @Mock
    private lateinit var fuelable: Fuelable

    private lateinit var burnFuel: BurnFuel

    @BeforeEach
    fun setup() {
        burnFuel = BurnFuel(fuelable)
    }

    @Test
    fun `burn fuel command test`() {
        whenever(fuelable.getFuelLevel()).thenReturn(5)
        whenever(fuelable.getFuelConsumption()).thenReturn(2)

        burnFuel.execute()

        verify(fuelable).setFuelLevel(eq(3))
    }
}
