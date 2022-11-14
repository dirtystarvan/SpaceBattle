package internals

import org.jetbrains.bio.viktor.F64FlatArray

interface Moveable {
    fun getPosition() : F64FlatArray
    fun getVelocity() : F64FlatArray
    fun setPosition(position: F64FlatArray)
}