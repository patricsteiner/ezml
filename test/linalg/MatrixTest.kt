package linalg

import org.testng.annotations.Test
import kotlin.test.*

class MatrixTest {

    @Test fun initWithSupplier() {
        val m = Matrix(3, 4, init = {0.5})
        assertEquals(m.width, 4)
        assertEquals(m.height, 3)
        m.forEach { assertEquals(0.5, it) }
    }

    @Test fun initWithData() {
        val m = Matrix(3, 2, data = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
        var n = 1.0
        for (i in 0 until 3) {
            for (j in 0 until 2) {
                assertEquals(n++, m[i, j])
            }
        }
    }

    @Test fun get() {
        val m = Matrix(2, 3)
        m[1, 0]
        m[1, 2]
        assertFails { m[1, 3] }
        assertFails { m[2, 1] }
        assertFails { m[2, 3] }
        assertFails { m[-1, 1] }
        assertFails { m[0, -1] }
        assertFails { m[0, 3] }
        assertFails { m[-1, 5] }
    }

    @Test fun forEach() {
        val m = Matrix(20, 30, {0.25})
        var sum = 0.0
        m.forEach { sum += it }
        assertEquals(150.0, sum)
    }

    @Test fun map() {
        val m = Matrix(2, 3, {0.0})
        val mapped = m.map({2.0})
        m.forEach { assertEquals(0.0, it) }
        mapped.forEach { assertEquals(2.0, it) }
        m.map({3.0}, true)
        m.forEach { assertEquals(3.0, it) }
    }

    @Test fun zip() {
        val m1 = Matrix(2, 3, {1.0})
        val m2 = Matrix(2, 3, {2.0})
        val m3 = Matrix(3, 2)
        assertFails { m1.zip(m3, {_, _ -> 0.0}) }
        val zipped = m1.zip(m2, {a, b -> a + b})
        m1.forEach { assertEquals(1.0, it) }
        m2.forEach { assertEquals(2.0, it) }
        zipped.forEach { assertEquals(3.0, it) }
        m1.zip(m2, {a, b -> a + b}, true)
        m1.forEach { assertEquals(3.0, it) }
        m2.forEach { assertEquals(2.0, it) }
    }

    @Test fun plusNumber() {
        val m = Matrix(2, 3, {0.0})
        val r = m + 5
        val s = m + -2.2
        m.forEach { assertEquals(0.0, it) }
        r.forEach { assertEquals(5.0, it) }
        s.forEach { assertEquals(-2.2, it) }
    }

    @Test fun plusAssignNumber() {
        val m = Matrix(2, 3, {0.0})
        m += 5
        m += -2.5
        m.forEach { assertEquals(2.5, it) }
    }

    @Test fun numberPlus() {
        val m = Matrix(2, 3, {1.0})
        val r = 5 + m
        val s = -5.25f + m
        m.forEach { assertEquals(1.0, it) }
        r.forEach { assertEquals(6.0, it) }
        s.forEach { assertEquals(-4.25, it) }
    }

    @Test fun minusNumber() {
        val m = Matrix(2, 3, {0.0})
        val r = m - 5
        val s = m - -2.2
        m.forEach { assertEquals(0.0, it) }
        r.forEach { assertEquals(-5.0, it) }
        s.forEach { assertEquals(2.2, it) }
    }

    @Test fun minusAssignNumber() {
        val m = Matrix(2, 3, {0.0})
        m -= 5
        m -= -2.5
        m.forEach { assertEquals(-2.5, it) }
    }

    @Test fun numberMinus() {
        val m = Matrix(2, 3, {1.0})
        val r = 5 - m
        val s = -5.25f - m
        m.forEach { assertEquals(1.0, it) }
        r.forEach { assertEquals(4.0, it) }
        s.forEach { assertEquals(-6.25, it) }
    }

    @Test fun timesNumber() {
        val m = Matrix(2, 3, {2.0})
        val r = m * 5
        val s = m * -1
        m.forEach { assertEquals(2.0, it) }
        r.forEach { assertEquals(10.0, it) }
        s.forEach { assertEquals(-2.0, it) }
    }

    @Test fun timesAssignNumber() {
        val m = Matrix(2, 3, {2.0})
        m *= 5
        m *= -1
        m.forEach { assertEquals(-10.0, it) }
    }

    @Test fun numberTimes() {
        val m = Matrix(2, 3, {2.0})
        val r = 5 * m
        val s = -2 * m
        m.forEach { assertEquals(2.0, it) }
        r.forEach { assertEquals(10.0, it) }
        s.forEach { assertEquals(-4.0, it) }
    }

    @Test fun divNumber() {
        val m = Matrix(2, 3, {12.0})
        val r = m / 3
        val s = m / -4
        m.forEach { assertEquals(12.0, it) }
        r.forEach { assertEquals(4.0, it) }
        s.forEach { assertEquals(-3.0, it) }
    }

    @Test fun divAssignNumber() {
        val m = Matrix(2, 3, {9.0})
        m /= 3
        m /= -2
        m.forEach { assertEquals(-1.5, it) }
    }

    @Test fun numberDiv() {
        val m = Matrix(2, 3, {3.0})
        val r = 12 / m
        val s = -6 / m
        m.forEach { assertEquals(3.0, it) }
        r.forEach { assertEquals(4.0, it) }
        s.forEach { assertEquals(-2.0, it) }
    }

    @Test fun unaryMinus() {
        val m = Matrix(2, 3, {3.0})
        val r = -m
        m.forEach { assertEquals(3.0, it) }
        r.forEach { assertEquals(-3.0, it) }
    }

    @Test fun copy() {
        val m = Matrix(2, 3, {3.0})
        val r = m.copy()
        r.map({6.0}, true)
        assertEquals(2, r.height)
        assertEquals(3, r.width)
        m.forEach { assertEquals(3.0, it) }
        r.forEach { assertEquals(6.0, it) }
    }

    @Test fun equals() { // TODO how to properly test equals? == vs === vs equals()?
        val m = Matrix(2, 3, {3.0})
        val r = Matrix(3, 2, {3.0})
        val s = m.copy()
        assertTrue(m.equals(s))
        assertTrue(m == s)
        assertEquals(m, s)
        s.map({6.0}, true)
        assertFalse(m.equals(s))
        assertFalse(m == s)
        assertNotEquals(m, s)
        assertFalse(m.equals(r))
        assertFalse(m == r)
        assertNotEquals(m, r)
    }
}