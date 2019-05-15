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

package pl.touk.android.bubble.coordinates

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log

typealias Radian = Float
typealias RadianArray = FloatArray
typealias Degrees = Float

class CoordinatesCalculator {

    companion object {
        private const val AZIMUTH_POSITION = 0
        private const val PITCH_POSITION = 1
        private const val ROLL_POSITION = 2
        private const val SENSOR_RESULTS_SIZE = 3
        private const val ORIENTATION_COORDINATES_RESULT_SIZE = 3
        private const val ROTATION_MATRIX_SIZE = 9
    }

    private var accelerometerSensorIndication: FloatArray? = FloatArray(SENSOR_RESULTS_SIZE)
    private var magneticSensorIndication: FloatArray? = FloatArray(SENSOR_RESULTS_SIZE)

    fun calculate(sensorEvent: SensorEvent): Coordinates {
        val start = System.currentTimeMillis()
        cacheEventData(sensorEvent)

        if (magneticSensorIndication != null && accelerometerSensorIndication != null) {
            var deviceOrientation = calculateDeviceOrientation()
            return deviceOrientation.run { Coordinates(pitch, roll, azimuth) }.also {
                Log.d("AvgCalc", "time [${System.currentTimeMillis() - start}]")
            }
        }
        return Coordinates.default().also {
            Log.d("AvgCalc", "time [${System.currentTimeMillis() - start}]")
        }
    }

    private fun calculateDeviceOrientation(): RadianArray {
        var gravityRotationalData = FloatArray(ROTATION_MATRIX_SIZE)
        var magneticRotationalData = FloatArray(ROTATION_MATRIX_SIZE)
        SensorManager.getRotationMatrix(gravityRotationalData, magneticRotationalData, accelerometerSensorIndication, magneticSensorIndication)
        val remappedRotationMatrix = RadianArray(ROTATION_MATRIX_SIZE)
        SensorManager.remapCoordinateSystem(gravityRotationalData, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix)
        val orientation = RadianArray(ORIENTATION_COORDINATES_RESULT_SIZE)
        SensorManager.getOrientation(remappedRotationMatrix, orientation)
        return orientation
    }

    private val RadianArray.azimuth: Degrees
        get() = this[AZIMUTH_POSITION].toDegrees()

    private val RadianArray.pitch: Degrees
        get() = this[PITCH_POSITION].toDegrees()

    private val RadianArray.roll: Degrees
        get() = this[ROLL_POSITION].toDegrees()

    private fun Radian.toDegrees() = java.lang.Math.toDegrees(this.toDouble()).toFloat()


    private fun cacheEventData(sensorEvent: SensorEvent) {
        when (sensorEvent.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD -> magneticSensorIndication = sensorEvent.values.clone()
            Sensor.TYPE_ACCELEROMETER -> accelerometerSensorIndication = sensorEvent.values.clone()
        }
    }

    internal fun calculateAverage(coordinates: List<Coordinates>): Coordinates {
        var averagePitch = 0f
        var averageRoll = 0f
        var averageZ = 0f
        val size = coordinates.size.toFloat()

        coordinates.forEach {
            averagePitch += it.pitch
            averageRoll += it.roll
            averageZ += it.z
        }

        return Coordinates(averagePitch / size, averageRoll / size, averageZ / size )
    }
}