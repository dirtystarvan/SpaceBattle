package commands

import internals.Rotatable

class Rotate(private val roteable: Rotatable) {
    fun execute() {
        roteable.setDirection((roteable.getDirection() + roteable.getAngularVelocity()) % roteable.getDirectionsNumber())
    }
}