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

import android.util.Log

enum class Orientation(vararg val ranges: ClosedFloatingPointRange<Float>) {
    PORTRAIT(-45F..45F),
    REVERSE_PORTRAIT(-180F..-135F, 135F..180F),
    LANDSCAPE(-135F..-45F),
    REVERSE_LANDSCAPE(45F..135F),
    UNDEFINED(0F..0F);

    companion object {
        fun fetchForRoll(rollDegrees: Float): Orientation {
            return Orientation.values().firstOrNull {
                it.ranges.any { range -> range.contains(rollDegrees) }
            } ?: UNDEFINED
        }
    }

}