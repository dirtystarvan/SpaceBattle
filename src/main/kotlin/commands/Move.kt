package commands

import internals.Moveable

class Move(private val moveable: Moveable): ICommand {
    override fun execute() {
        moveable.setPosition(moveable.getPosition() + moveable.getVelocity())
    }
}
