package linalg

import java.util.*

class Matrix(val height: Int, val width: Int, init: (Int) -> Double = {0.0}, private val data: DoubleArray = DoubleArray(height * width, init)) {

    init {
        if (data.size != height * width) throw IllegalArgumentException("data does not match size")
    }

    operator fun get(row: Int, col: Int): Double {
        if (row < 0 || row >=height || col < 0 || col >= width) throw IndexOutOfBoundsException()
        return data[width * row + col]
    }

    private operator fun set(row: Int, col: Int, value: Double) {
        if (row < 0 || row >=height || col < 0 || col >= width) throw IndexOutOfBoundsException()
        data[width * row + col] = value
    }

    fun forEach(action: (Double) -> Unit) {
        data.forEach(action)
    }

    fun map(f: (Double) -> Double, inPlace: Boolean = false): Matrix {
        val res = if (inPlace) this else copy()
        for (i in 0 until res.data.size) {
            res.data[i] = f(res.data[i])
        }
        return res
    }

    operator fun plusAssign(n: Number) {
        map({it + n.toDouble()}, true)
    }

    operator fun minusAssign(n: Number) {
        map({it - n.toDouble()}, true)
    }

    operator fun timesAssign(n: Number) {
        map({it * n.toDouble()}, true)
    }

    operator fun divAssign(n: Number) {
        map({it / n.toDouble()}, true)
    }

    operator fun plus(n: Number): Matrix {
        return map({it + n.toDouble()}, false)
    }

    operator fun minus(n: Number): Matrix {
        return map({it - n.toDouble()}, false)
    }

    operator fun times(n: Number): Matrix {
        return map({it * n.toDouble()}, false)
    }

    operator fun div(n: Number): Matrix {
        return map({it / n.toDouble()}, false)
    }

    operator fun unaryMinus(): Matrix {
        return map({-it})
    }

    fun copy(): Matrix {
        return Matrix(height, width, data = Arrays.copyOf(data,  data.size))
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Matrix
        if (width != other.width) return false
        if (height != other.height) return false
        if (!Arrays.equals(data, other.data)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + Arrays.hashCode(data)
        return result
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
    return matrix.map({this.toDouble()/it}, false)
}
