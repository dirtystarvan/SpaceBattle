package internals

interface Fuelable {
    fun getFuelLevel(): Int
    fun getFuelConsumption(): Int

    fun setFuelLevel(amount: Int)
}
