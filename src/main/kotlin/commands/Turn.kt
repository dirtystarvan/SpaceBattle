package commands

import internals.Turnable

/* Описывает изменение направления движения в сторону поворота */
class Turn(private val turnable: Turnable): ICommand {
    override fun execute() {
        turnable.setVelocityAngle(turnable.getDirection())
    }
}
