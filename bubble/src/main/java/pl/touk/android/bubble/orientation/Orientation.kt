package pl.touk.android.bubble.orientation

public enum class Orientation() {
    PORTRAIT,
    REVERSE_PORTRAIT,
    LANDSCAPE,
    REVERSE_LANDSCAPE,
    UNDEFINED;

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