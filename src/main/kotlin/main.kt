import org.jetbrains.bio.viktor.F64Array

fun main() {
    val a = F64Array.of(1.0, 0.0)
    val b = F64Array.of(0.0, 1.0)

    println(a + b)
}