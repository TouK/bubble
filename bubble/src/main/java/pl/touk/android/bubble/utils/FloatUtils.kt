package pl.touk.android.bubble.utils

fun Float.inRange(min: Float, max: Float): Boolean {
    return this > min && this < max
}
