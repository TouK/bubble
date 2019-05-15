package pl.touk.android.bubble

import android.hardware.SensorManager

data class BubbleSettings(val samplingPeriodUs: Int = SensorManager.SENSOR_DELAY_UI)