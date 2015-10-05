package pl.touk.android.bubble

import pl.touk.android.bubble.utils.almostEqual


public enum class Orientation(val rotation: Float) {

    PORTRAIT(0f),
    LANDSCAPE(90f);

    companion object {
        private val X_LANDSCAPE = 0f
        private val Y_LANDSCAPE = -1.5f
        private val TOLERANCE = 0.4f

        fun extract(xAxisValue: Float, yAxisValue: Float): Orientation {
            if (xAxisValue.almostEqual(X_LANDSCAPE, TOLERANCE)
                    && yAxisValue.almostEqual(Y_LANDSCAPE, TOLERANCE)) {
                return LANDSCAPE
            } else {
                return PORTRAIT
            }
        }
    }
}