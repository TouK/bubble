package pl.touk.jointly.utils

fun Float.almostEqual(value: Float, tolerance: Float): Boolean {
    return Math.abs(this - value) <= tolerance
}
