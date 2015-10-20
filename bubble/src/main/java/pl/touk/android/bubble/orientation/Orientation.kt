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