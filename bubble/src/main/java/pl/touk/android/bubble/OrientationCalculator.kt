package pl.touk.android.bubble

import pl.touk.android.bubble.utils.almostEqual


class OrientationCalculator {

    companion object Degree {
        val MINUS_90 = (-Math.PI/2).toFloat()
        val ZERO = 0f
    }

    internal fun calculate(coordinates: Coordinates): Orientation {
        if (coordinates.roll.almostEqual(Degree.MINUS_90)) {
            return Orientation.LANDSCAPE
        } else {
            return Orientation.PORTRAIT
        }
    }
}