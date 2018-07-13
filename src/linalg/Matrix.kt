package linalg

import java.util.*

class Matrix(val height: Int, val width: Int, init: (Int) -> Double = {0.0}, private val data: DoubleArray = DoubleArray(height * width, init)) {

    init {
        if (data.size != height * width) throw IllegalArgumentException("data does not match size")
    }

    val T get() = transpose()

    operator fun get(row: Int, col: Int): Double {
        if (row < 0 || row >=height || col < 0 || col >= width) throw IndexOutOfBoundsException("invalid index")
        return data[width * row + col]
    }

    private operator fun set(row: Int, col: Int, value: Double) {
        if (row < 0 || row >=height || col < 0 || col >= width) throw IndexOutOfBoundsException("invalid index")
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

    fun zip(m: Matrix, f: (Double, Double) -> Double, inPlace: Boolean = false): Matrix {
        if (!equalShape(m)) throw IllegalArgumentException("Shapes must match") // TODO use typed exception
        val res = if (inPlace) this else copy()
        for (i in 0 until data.size) {
            res.data[i] = f(res.data[i], m.data[i])
        }
        return res
    }

    operator fun plusAssign(n: Number) {
        map({it + n.toDouble()}, true)
    }

    operator fun plusAssign(m: Matrix) {
        zip(m, {a, b -> a + b}, true)
    }

    operator fun minusAssign(n: Number) {
        map({it - n.toDouble()}, true)
    }

    operator fun minusAssign(m: Matrix) {
        zip(m, {a, b -> a - b}, true)
    }

    operator fun timesAssign(n: Number) {
        map({it * n.toDouble()}, true)
    }

    operator fun timesAssign(m: Matrix) {
        zip(m, {a, b -> a * b}, true)
    }

    operator fun divAssign(n: Number) {
        map({it / n.toDouble()}, true)
    }

    operator fun divAssign(m: Matrix) {
        zip(m, {a, b -> a / b}, true)
    }

    operator fun plus(n: Number): Matrix {
        return map({it + n.toDouble()}, false)
    }

    operator fun plus(m: Matrix): Matrix {
        return zip(m, {a, b -> a + b}, false)
    }

    operator fun minus(n: Number): Matrix {
        return map({it - n.toDouble()}, false)
    }

    operator fun minus(m: Matrix): Matrix {
        return zip(m, {a, b -> a - b}, false)
    }

    operator fun times(n: Number): Matrix {
        return map({it * n.toDouble()}, false)
    }

    operator fun times(m: Matrix): Matrix {
        return zip(m, {a, b -> a * b}, false)
    }

    operator fun div(n: Number): Matrix {
        return map({it / n.toDouble()}, false)
    }

    operator fun div(m: Matrix): Matrix {
        return zip(m, {a, b -> a / b}, false)
    }

    operator fun unaryMinus(): Matrix {
        return map({-it})
    }

    infix fun X(m: Matrix): Matrix {
        if (width != m.height) throw IllegalArgumentException("dimensions do not agree (${height}x${width} X ${m.height}x${m.width})")
        val res = DoubleArray(height * m.width)
        for (i in 0 until height) {
            for (j in 0 until width) {
                for (k in 0 until m.width) { // TODO is there a mistake in the array access? can be out of bound sometimes...
                    res[height * i + k] += this[i, j] * m[j, k]
                }
            }
        }
        return Matrix(height, m.width, data = res)
    }

    fun transpose(): Matrix {
        val res = Matrix(width, height)
        for (i in 0 until height) {
            for (j in 0 until width) {
                res[j, i] = this[i, j]
            }
        }
        return res
    }

    fun equalShape(other: Matrix): Boolean { // TODO should this be nullable?
        return width == other.width && height == other.height
    }

    fun copy(): Matrix {
        return Matrix(height, width, data = Arrays.copyOf(data,  data.size))
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in 0 until height) {
            for (j in 0 until width) {
                sb.append(String.format("%.4f", this[i, j]))
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

fun Vector(vararg data: Double): Matrix {
    return Matrix(1, data.size, data = data)
}

fun Vector(length: Int, init: (Int) -> Double = {0.0}): Matrix {
    return Matrix(1, length, init)
}