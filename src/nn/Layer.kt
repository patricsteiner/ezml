package nn

import linalg.Matrix

class Layer(val size: Int, val hasBias: Boolean, val activation: (Double, Boolean) -> Double = ::linear) {

    // TODO property size should return +1 if hasBias, so checks elsewhere can be avoided

    lateinit var values: Matrix
    lateinit var weights: Matrix

    fun initWeights(prevLayerSize: Int) {
        weights = Matrix(prevLayerSize, size, { Math.random()/2 - .25 }) // TODO allow custom initialisation
    }
}