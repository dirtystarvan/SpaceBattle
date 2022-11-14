package commands

import internals.Moveable
import org.jetbrains.bio.viktor.F64Array
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class MoveCommandTest {
    @Mock
    private lateinit var movable: Moveable

    private lateinit var moveCmd: Move

    @BeforeEach
    fun setup() {
        moveCmd = Move(movable)
    }

    @Test
    fun `move from (12,5) with V(-7,3) results (5,8)`() {
        whenever(movable.getPosition()).thenReturn(F64Array.of(12.0, 5.0))
        whenever(movable.getVelocity()).thenReturn(F64Array.of(-7.0, 3.0))

        moveCmd.execute()

        verify(movable, times(1))
            .setPosition(check {
                assertEquals(2, it.length)
                assertEquals(5.0, it[0])
                assertEquals(8.0, it[1])
        })
    }

    @Test
    fun `cannot get position exception`() {
        whenever(movable.getPosition()).thenThrow(RuntimeException("Can't supply position"))

        assertThrows<Exception> { moveCmd.execute() }
    }

    @Test
    fun `cannot get velocity exception`() {
        whenever(movable.getPosition()).thenReturn(F64Array.of(12.0, 5.0))
        whenever(movable.getVelocity()).thenThrow(RuntimeException("Can't supply velocity"))

        assertThrows<Exception> { moveCmd.execute() }
    }

    @Test
    fun `cannot set new position`() {
        whenever(movable.getPosition()).thenReturn(F64Array.of(12.0, 5.0))
        whenever(movable.getVelocity()).thenReturn(F64Array.of(-7.0, 3.0))
        whenever(movable.setPosition(any())).thenThrow(RuntimeException("Can't set the position"))

        assertThrows<Exception> { moveCmd.execute() }
    }
}
