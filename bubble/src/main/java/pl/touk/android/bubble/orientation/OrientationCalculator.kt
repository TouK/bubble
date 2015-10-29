package pl.touk.android.bubble.orientation

import pl.touk.android.bubble.Degree
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.utils.inRange

open class OrientationCalculator {

    open fun calculate(coordinates: Coordinates): Orientation {
        return when {
            coordinates.pitch <= Degree.MINUS_45 -> Orientation.PORTRAIT
            coordinates.pitch >= Degree.PLUS_45 -> Orientation.REVERSE_PORTRAIT
            coordinates.roll <= Degree.MINUS_45 -> Orientation.LANDSCAPE
            coordinates.roll >= Degree.PLUS_45 -> Orientation.REVERSE_LANDSCAPE
            else -> Orientation.PORTRAIT
        }
    }
}