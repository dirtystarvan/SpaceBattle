package commands

import internals.Rotatable

/* Описывает поворот вокруг своей оси */
class Rotate(private val roteable: Rotatable): ICommand {
    override fun execute() {
        roteable.setDirection((roteable.getDirection() + roteable.getAngularVelocity()) % roteable.getDirectionsNumber())
    }
}
