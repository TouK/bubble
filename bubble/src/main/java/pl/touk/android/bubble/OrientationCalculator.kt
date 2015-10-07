package pl.touk.android.bubble

class OrientationCalculator {

    companion object Degree {
        val MINUS_45 = (-Math.PI/4).toFloat()
    }

    internal fun calculate(coordinates: Coordinates): Orientation {
        return when {
            coordinates.pitch <= Degree.MINUS_45 -> Orientation.PORTRAIT
            coordinates.roll <= Degree.MINUS_45 -> Orientation.LANDSCAPE
            else -> Orientation.PORTRAIT
        }
    }
}