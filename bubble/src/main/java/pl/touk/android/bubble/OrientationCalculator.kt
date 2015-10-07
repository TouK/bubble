package pl.touk.android.bubble

open class OrientationCalculator {

    open fun calculate(coordinates: Coordinates): Orientation {
        return when {
            coordinates.pitch <= Degree.MINUS_45 -> Orientation.PORTRAIT
            coordinates.roll <= Degree.MINUS_45 -> Orientation.LANDSCAPE
            else -> Orientation.PORTRAIT
        }
    }
}