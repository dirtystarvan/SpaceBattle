import commands.Rotate
import internals.Rotatable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class RotateCommandTest {
    @Mock
    private lateinit var rotable: Rotatable

    private lateinit var rotateCmd: Rotate

    @BeforeEach
    fun setup() {
        rotateCmd = Rotate(rotable)
    }

    @Test
    fun `rotate 360`() {
        whenever(rotable.getDirection()).thenReturn(1)
        whenever(rotable.getAngularVelocity()).thenReturn(8)
        whenever(rotable.getDirectionsNumber()).thenReturn(8)

        rotateCmd.execute()

        verify(rotable).setDirection(eq(1))
    }

    @Test
    fun `cannot get direction exception`() {
        whenever(rotable.getDirection()).thenThrow(RuntimeException("Cannot supply direction"))

        assertThrows<Exception> { rotateCmd.execute() }
    }

    @Test
    fun `cannot get angular velocity exception`() {
        whenever(rotable.getDirection()).thenReturn(1)
        whenever(rotable.getAngularVelocity()).thenThrow(RuntimeException("Cannot supply angular velocity"))

        assertThrows<Exception> { rotateCmd.execute() }
    }

    @Test
    fun `cannot get directions number exception`() {
        whenever(rotable.getDirection()).thenReturn(1)
        whenever(rotable.getAngularVelocity()).thenReturn(8)
        whenever(rotable.getDirectionsNumber()).thenThrow(RuntimeException("Cannot supply number of directions"))

        assertThrows<Exception> { rotateCmd.execute() }
    }

    @Test
    fun `cannot set new direction exception`() {
        whenever(rotable.getDirection()).thenReturn(1)
        whenever(rotable.getAngularVelocity()).thenReturn(8)
        whenever(rotable.getDirectionsNumber()).thenReturn(8)
        whenever(rotable.setDirection(any())).thenThrow(RuntimeException("Cannot set new direction"))

        assertThrows<Exception> { rotateCmd.execute() }
    }
}