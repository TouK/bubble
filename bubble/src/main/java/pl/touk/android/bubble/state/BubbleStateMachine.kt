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
        orientation = when (orientation) {
            Orientation.UNDEFINED   -> orientationCalculator.calculate(coordinates)
            Orientation.PORTRAIT    -> stateAfterPortrait(coordinates)
            Orientation.REVERSE_PORTRAIT   -> stateAfterPortrait(coordinates)
            Orientation.LANDSCAPE   -> stateAfterLandscape(coordinates)
            Orientation.REVERSE_LANDSCAPE   -> stateAfterReverseLandscape(coordinates)
            else                    -> orientation
        }
        return oldOrientation != orientation
    }

    private fun stateAfterLandscape(coordinates: Coordinates): Orientation {
        if (shouldStayInLandscape(coordinates)) {
            return Orientation.LANDSCAPE
        } else if(coordinates.pitch < Degree.MINUS_45) {
            return Orientation.PORTRAIT
        } else if (coordinates.pitch > Degree.PLUS_45) {
            return Orientation.REVERSE_PORTRAIT
        }else {
            return Orientation.REVERSE_LANDSCAPE
        }
    }

    private fun stateAfterReverseLandscape(coordinates: Coordinates): Orientation {
        if (shouldStayInReverseLandscape(coordinates)) {
            return Orientation.REVERSE_LANDSCAPE
        } else if(coordinates.pitch < Degree.MINUS_45) {
            return Orientation.PORTRAIT
        } else if (coordinates.pitch > Degree.PLUS_45) {
            return Orientation.REVERSE_PORTRAIT
        }else {
            return Orientation.LANDSCAPE
        }
    }

    private fun shouldStayInLandscape(coordinates: Coordinates)
            = coordinates.pitch.inRange(Degree.MINUS_45, Degree.PLUS_45) &&
              coordinates.roll < Degree.PLUS_45

    private fun shouldStayInReverseLandscape(coordinates: Coordinates)
            = coordinates.pitch.inRange(Degree.MINUS_45, Degree.PLUS_45) &&
              coordinates.roll > Degree.MINUS_45

    private fun stateAfterPortrait(coordinates: Coordinates): Orientation {
        if (!shouldChange(coordinates)) {
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

    private fun shouldChange(coordinates: Coordinates): Boolean {
        return when (orientation) {
            Orientation.PORTRAIT    -> !shouldStayPortrait(coordinates)
            Orientation.LANDSCAPE   -> !shouldStayInLandscape(coordinates)
            Orientation.REVERSE_PORTRAIT   -> !shouldStayReversePortrait(coordinates)
            Orientation.REVERSE_LANDSCAPE   -> !shouldStayInReverseLandscape(coordinates)
            else                    -> false
        }
    }

    private fun shouldStayPortrait(coordinates: Coordinates)
            = coordinates.pitch < Degree.MINUS_45 ||
            (coordinates.roll.inRange(Degree.MINUS_45, Degree.PLUS_45) &&
                    coordinates.pitch.inRange(Degree.MINUS_45, Degree.PLUS_45))

    private fun shouldStayReversePortrait(coordinates: Coordinates)
            = coordinates.pitch >= Degree.PLUS_45 ||
                (coordinates.roll.inRange(Degree.MINUS_45, Degree.PLUS_45) &&
                        coordinates.pitch.inRange(Degree.MINUS_45, Degree.PLUS_45))

}