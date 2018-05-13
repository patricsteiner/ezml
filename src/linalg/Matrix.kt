package linalg

import java.util.*

class Matrix(val width: Int, val height: Int, init: (Int) -> Double = {0.0}, private val data: DoubleArray = DoubleArray(width * height, init)) {

    init {
        if (data.size != width*height) throw IllegalArgumentException("data does not match size")
    }

    operator fun get(row: Int, col: Int): Double {
        return data[row * col + col]
    }

    private operator fun set(row: Int, col: Int, value: Double) {
        data[row * col + col] = value
    }

    fun map(function: (Double) -> Double, copy: Boolean = true): Matrix {
        val res = if (copy) copy() else this
        for (row in 0 until width) {
            for (col in 0 until height) {
                res[row, col] = function(this[row, col])
            }
        }
        return res
    }

    operator fun plusAssign(n: Number) {
        map({it + n.toDouble()}, false)
    }

    operator fun minusAssign(n: Number) {
        map({it - n.toDouble()}, false)
    }

    operator fun timesAssign(n: Number) {
        map({it * n.toDouble()}, false)
    }

    operator fun divAssign(n: Number) {
        map({it / n.toDouble()}, false)
    }

    operator fun plus(n: Number): Matrix {
        return map({it + n.toDouble()}, true)
    }

    operator fun minus(n: Number): Matrix {
        return map({it - n.toDouble()}, true)
    }

    operator fun times(n: Number): Matrix {
        return map({it * n.toDouble()}, true)
    }

    operator fun div(n: Number): Matrix {
        return map({it / n.toDouble()}, true)
    }

    operator fun unaryMinus(): Matrix {
        return map({-it})
    }

    fun copy(): Matrix {
        return Matrix(width, height, data = Arrays.copyOf(data,  data.size))
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in 0 until width) {
            for (col in 0 until height) {
                sb.append(String.format("%.4f", this[row, col]))
                sb.append("\t\t")
            }
            sb.append("\n")
        }
        return sb.toString()
    }
}

operator fun Number.plus(matrix: Matrix): Matrix {
    return matrix + this
}

operator fun Number.minus(matrix: Matrix): Matrix {
    return -matrix + this
}

operator fun Number.times(matrix: Matrix): Matrix {
    return matrix * this
}

operator fun Number.div(matrix: Matrix): Matrix {
    return matrix.map({this.toDouble()/it}, true)
}
