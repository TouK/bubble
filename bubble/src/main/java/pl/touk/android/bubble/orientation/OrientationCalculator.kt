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