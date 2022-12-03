package commands

import exception.CommandException
import internals.Fuelable

class CheckFuel(private val fuelable: Fuelable): ICommand {
    override fun execute() {
        if (fuelable.getFuelLevel() < 0) {
            throw CommandException("Does not have enough fuel!!")
        }
    }
}
