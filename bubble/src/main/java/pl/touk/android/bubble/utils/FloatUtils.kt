package pl.touk.android.bubble.utils

fun Float.almostEqual(value: Float, tolerance: Float): Boolean {
    return Math.abs(this - value) <= tolerance
}
