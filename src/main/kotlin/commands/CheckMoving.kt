package commands

import exception.CommandException
import internals.Moveable
import org.jetbrains.bio.viktor.F64FlatArray
import kotlin.math.absoluteValue

class CheckMoving(private val moveable: Moveable):ICommand {
    private val epsilon = Double.MIN_VALUE * 2

    override fun execute() {
        if (moveable.getVelocity().isZero()) {
            throw CommandException("This object does not move!")
        }
    }

    private fun F64FlatArray.isZero(): Boolean =
        this[0].isActuallyZero() && this[1].isActuallyZero()

    private fun Double.isActuallyZero(): Boolean {
        return this.absoluteValue < epsilon
    }
}
