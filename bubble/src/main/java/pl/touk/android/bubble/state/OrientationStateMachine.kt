package pl.touk.android.bubble.state

import pl.touk.android.bubble.Coordinates
import pl.touk.android.bubble.Degree
import pl.touk.android.bubble.Orientation
import pl.touk.android.bubble.OrientationCalculator


class OrientationStateMachine {

    internal var orientationCalculator = OrientationCalculator()

    var orientation = Orientation.UNDEFINED

    fun update(coordinates: Coordinates): Boolean {
        val oldOrientation = orientation
        orientation = when (orientation) {
            Orientation.UNDEFINED -> orientationCalculator.calculate(coordinates)
            else -> calculate(coordinates)
        }
        return oldOrientation != orientation
    }

    private fun calculate(coordinates: Coordinates): Orientation {
        return when (orientation) {
            Orientation.PORTRAIT ->
                if (coordinates.pitch < Degree.MINUS_45 ||
                        (coordinates.pitch >= Degree.MINUS_45 && coordinates.roll > Degree.MINUS_45)) {
                    Orientation.PORTRAIT
                } else {
                    Orientation.LANDSCAPE
                }
            Orientation.LANDSCAPE ->
                if (coordinates.pitch >= Degree.MINUS_45) Orientation.LANDSCAPE else Orientation.PORTRAIT
            else ->
                orientation
        }
    }
}