package commands

import internals.Fuelable

class BurnFuel(private val fuelable: Fuelable): ICommand {
    override fun execute() {
        fuelable.setFuelLevel(fuelable.getFuelLevel() - fuelable.getFuelConsumption())
    }
}
