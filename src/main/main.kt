package main

import linalg.Matrix
import linalg.Vector
import nn.*

fun main(args: Array<String>) {
    val nn = NeuralNetwork()
    nn.addLayer(Layer(2, true))
    nn.addLayer(Layer(3, true))
    nn.addLayer(Layer(1, false))

    val x = Matrix(4, 2, data = doubleArrayOf(0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0))
    val y = Vector(0.0, 1.0, 1.0, 0.0).T

    nn.forwardPropagate(x)

    //nn.fit(x, y)

    //println(nn.predict(x))
}

