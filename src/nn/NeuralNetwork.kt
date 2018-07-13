package nn

import linalg.Matrix

class NeuralNetwork {

    var layers: ArrayList<Layer> = ArrayList()

    fun addLayer(layer: Layer) {
        if (layers.size > 0) {
            layer.initWeights(layers.last().size /*TODO+ if (layers.last().hasBias) 1 else 0*/)
        }
        layers.add(layer)
    }

    fun forwardPropagate(x: Matrix) {
        layers[0].values = x
        for (i in 1 until layers.size) {
            val prevLayerActivated = layers[i-1].values.map({ layers[i-1].activation(it, false) })
            layers[i].values = (prevLayerActivated X layers[i].weights)
        }
    }

    fun fit(x: Matrix, y: Matrix) {
        TODO() //gradient descent with backpropagation
    }

    fun predict(x: Matrix): Matrix {
        TODO() //forwardpropagate and activate last layer
    }



}