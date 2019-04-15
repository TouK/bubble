/*
 * Copyright 2015 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

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

        if(coordinates.pitch < Orientation.PORTRAIT.treshold) {
            return Orientation.PORTRAIT
        } else if (coordinates.pitch > Orientation.REVERSE_PORTRAIT.treshold) {
            return Orientation.REVERSE_PORTRAIT
        }else {
            return orientation.opposite
        }
    }

    private fun stateAfterVertical(coordinates: Coordinates): Orientation {
        if (shouldNotChange(coordinates)) {
            return orientation
        }

        if (coordinates.roll < Orientation.LANDSCAPE.treshold) {
            return Orientation.LANDSCAPE
        } else if (coordinates.roll > Orientation.REVERSE_LANDSCAPE.treshold) {
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
                coordinates.roll < Orientation.REVERSE_LANDSCAPE.treshold

    private fun shouldStayInReverseLandscape(coordinates: Coordinates)
            = pitchIsNeutral(coordinates) &&
                coordinates.roll > Orientation.LANDSCAPE.treshold

    private fun shouldStayPortrait(coordinates: Coordinates)
            = coordinates.pitch < Orientation.PORTRAIT.treshold ||
                (rollIsNeutral(coordinates) && pitchIsNeutral(coordinates))

    private fun shouldStayReversePortrait(coordinates: Coordinates)
            = coordinates.pitch >= Orientation.REVERSE_PORTRAIT.treshold ||
                (rollIsNeutral(coordinates) && pitchIsNeutral(coordinates))

    private fun rollIsNeutral(coordinates: Coordinates)
            = coordinates.roll.inRange(Orientation.LANDSCAPE.treshold,
                                        Orientation.REVERSE_LANDSCAPE.treshold)

    private fun pitchIsNeutral(coordinates: Coordinates)
            = coordinates.pitch.inRange(Orientation.PORTRAIT.treshold,
                                        Orientation.REVERSE_PORTRAIT.treshold)

}