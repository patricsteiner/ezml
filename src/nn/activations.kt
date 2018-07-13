package nn

fun linear(x: Double, derivative: Boolean = false): Double {
     return if (derivative) 0.0 else x
}

//TODO add more activation functions (relu, sigmoid)