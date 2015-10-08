package pl.touk.android.bubble.state

import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.Degree
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.orientation.OrientationCalculator


class BubbleStateMachine {

    internal var orientationCalculator = OrientationCalculator()

    var orientation = Orientation.UNDEFINED

    fun update(coordinates: Coordinates): Boolean {
        val oldOrientation = orientation
        orientation = when (orientation) {
            Orientation.UNDEFINED   -> orientationCalculator.calculate(coordinates)
            Orientation.PORTRAIT    -> stateAfterPortrait(coordinates)
            Orientation.LANDSCAPE   -> stateAfterLandscape(coordinates)
            else                    -> orientation
        }
        return oldOrientation != orientation
    }

    private fun stateAfterLandscape(coordinates: Coordinates)
            = if (shouldStayInLandscape(coordinates)) Orientation.LANDSCAPE else Orientation.PORTRAIT

    private fun shouldStayInLandscape(coordinates: Coordinates)
            = coordinates.pitch >= Degree.MINUS_45

    private fun stateAfterPortrait(coordinates: Coordinates)
            = if (shouldStayPortrait(coordinates)) Orientation.PORTRAIT else Orientation.LANDSCAPE

    private fun shouldStayPortrait(coordinates: Coordinates)
            = coordinates.pitch < Degree.MINUS_45 || coordinates.roll > Degree.MINUS_45

}