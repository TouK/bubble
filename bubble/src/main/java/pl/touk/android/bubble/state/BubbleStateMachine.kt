package pl.touk.android.bubble.state

import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.Degree
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.orientation.OrientationCalculator
import pl.touk.android.bubble.utils.inRange

class BubbleStateMachine {

    var orientationCalculator = OrientationCalculator()

    var orientation = Orientation.UNDEFINED

    fun update(coordinates: Coordinates): Boolean {
        val oldOrientation = orientation
        orientation = when {
            orientation.isVertical      -> stateAfterVertical(coordinates)
            orientation.isHorizontal    -> stateAfterHorizontal(coordinates)
            else                        -> orientationCalculator.calculate(coordinates)
        }
        return oldOrientation != orientation
    }

    private fun stateAfterHorizontal(coordinates: Coordinates): Orientation {
        if (shouldNotChange(coordinates)) {
            return orientation
        }

        if(coordinates.pitch < Degree.MINUS_45) {
            return Orientation.PORTRAIT
        } else if (coordinates.pitch > Degree.PLUS_45) {
            return Orientation.REVERSE_PORTRAIT
        }else {
            return orientation.opposite
        }
    }

    private fun stateAfterVertical(coordinates: Coordinates): Orientation {
        if (shouldNotChange(coordinates)) {
            return orientation
        }

        if (coordinates.roll < Degree.MINUS_45) {
            return Orientation.LANDSCAPE
        } else if (coordinates.roll > Degree.PLUS_45) {
            return Orientation.REVERSE_LANDSCAPE
        } else {
            return orientation.opposite
        }
    }

    private fun shouldNotChange(coordinates: Coordinates): Boolean {
        return when (orientation) {
            Orientation.PORTRAIT    -> shouldStayPortrait(coordinates)
            Orientation.LANDSCAPE   -> shouldStayInLandscape(coordinates)
            Orientation.REVERSE_PORTRAIT   -> shouldStayReversePortrait(coordinates)
            Orientation.REVERSE_LANDSCAPE   -> shouldStayInReverseLandscape(coordinates)
            else                    -> true
        }
    }

    private fun shouldStayInLandscape(coordinates: Coordinates)
            = pitchIsNeutral(coordinates) &&
            coordinates.roll < Degree.PLUS_45

    private fun shouldStayInReverseLandscape(coordinates: Coordinates)
            = pitchIsNeutral(coordinates) &&
            coordinates.roll > Degree.MINUS_45

    private fun shouldStayPortrait(coordinates: Coordinates)
            = coordinates.pitch < Degree.MINUS_45 ||
            (rollIsNeutral(coordinates) && pitchIsNeutral(coordinates))

    private fun shouldStayReversePortrait(coordinates: Coordinates)
            = coordinates.pitch >= Degree.PLUS_45 ||
                (rollIsNeutral(coordinates) &&
                        pitchIsNeutral(coordinates))

    private fun rollIsNeutral(coordinates: Coordinates) = coordinates.roll.inRange(Degree.MINUS_45, Degree.PLUS_45)

    private fun pitchIsNeutral(coordinates: Coordinates) = coordinates.pitch.inRange(Degree.MINUS_45, Degree.PLUS_45)

}