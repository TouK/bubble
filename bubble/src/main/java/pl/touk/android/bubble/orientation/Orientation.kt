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

public enum class Orientation(val treshold: Float = 0f) {
    PORTRAIT(Degree.MINUS_45),
    REVERSE_PORTRAIT(Degree.PLUS_45),
    LANDSCAPE(Degree.MINUS_45),
    REVERSE_LANDSCAPE(Degree.PLUS_45),
    UNDEFINED();

    val opposite: Orientation   
        get() = when (this) {
            PORTRAIT            -> REVERSE_PORTRAIT
            REVERSE_PORTRAIT    -> PORTRAIT
            LANDSCAPE           -> REVERSE_LANDSCAPE
            REVERSE_LANDSCAPE   -> LANDSCAPE
            else                -> UNDEFINED
        }

    val isVertical: Boolean
        get() = this == PORTRAIT || this == REVERSE_PORTRAIT

    val isHorizontal: Boolean
        get() = this == LANDSCAPE || this == REVERSE_LANDSCAPE

}