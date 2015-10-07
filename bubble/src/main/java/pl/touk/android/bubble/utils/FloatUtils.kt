package pl.touk.android.bubble.utils

fun Float.almostEqual(value: Float, tolerance: Float = 0.01f): Boolean {
    return Math.abs(this - value) <= tolerance
}
