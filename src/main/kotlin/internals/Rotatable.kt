package internals

interface Rotatable {
    fun getDirection(): Int
    fun getAngularVelocity(): Int
    fun getDirectionsNumber(): Int

    fun setDirection(new: Int)
}