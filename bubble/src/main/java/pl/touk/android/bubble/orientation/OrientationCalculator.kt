package pl.touk.android.bubble.orientation

import pl.touk.android.bubble.Degree
import pl.touk.android.bubble.coordinates.Coordinates

open class OrientationCalculator {

    open fun calculate(coordinates: Coordinates): Orientation {
        return when {
            coordinates.pitch <= Degree.MINUS_45 -> Orientation.PORTRAIT
            coordinates.roll <= Degree.MINUS_45 -> Orientation.LANDSCAPE
            else -> Orientation.PORTRAIT
        }
    }
}