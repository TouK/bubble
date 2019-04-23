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


class CoordinatesCalculator {

    companion object {
        private val PITCH_POSITION = 1
        private val ROLL_POSITION = 2
        private val SENSOR_RESULTS_SIZE = 3
        private val ORIENTATION_COORDINATES_RESULT_SIZE = 3
        private val ROTATION_MATRIX_SIZE = 9
    }

    private val rotationMatrix: FloatArray = FloatArray(ROTATION_MATRIX_SIZE)
    private val orientationCoordinates: FloatArray = FloatArray(ORIENTATION_COORDINATES_RESULT_SIZE)


    var gravityRotationalData: FloatArray? = null
    var magneticRotationalData: FloatArray? = null
    var accelerometerSensorIndication: FloatArray? = FloatArray(SENSOR_RESULTS_SIZE)
    var magneticSensorIndication: FloatArray? = FloatArray(SENSOR_RESULTS_SIZE)
    var values: FloatArray? = FloatArray(3)

    var azimuth: Float = 0F
    var pitch: Float = 0F
    var roll: Float = 0F

    fun calculate(sensorEvent: SensorEvent): Coordinates {
        cacheEventData(sensorEvent)

        if (magneticSensorIndication != null && accelerometerSensorIndication != null) {
            gravityRotationalData = FloatArray(9)
            magneticRotationalData = FloatArray(9)
            SensorManager.getRotationMatrix(gravityRotationalData, magneticRotationalData, accelerometerSensorIndication, magneticSensorIndication)
            // Correct if screen is in Landscape

            val outR = FloatArray(9)
            SensorManager.remapCoordinateSystem(gravityRotationalData, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR)
            SensorManager.getOrientation(outR, values)

            azimuth = values!![0] * 57.2957795f //looks like we don't need this one
            pitch = values!![1] * 57.2957795f
            roll = values!![2] * 57.2957795f
            magneticSensorIndication = null //retrigger the loop when things are repopulated
            accelerometerSensorIndication = null ////retrigger the loop when things are repopulated
        }
        return Coordinates(pitch, roll, azimuth)
    }

//    fun calculate(sensorEvent: SensorEvent): Coordinates {
//        cacheEventData(sensorEvent)
//        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerSensorValues, magneticSensorValues);
//        SensorManager.getOrientation(rotationMatrix, orientationCoordinates);
//
//        val pitch = Math.toDegrees(orientationCoordinates[PITCH_POSITION].toDouble()).toFloat()
//        val roll = Math.toDegrees(orientationCoordinates[ROLL_POSITION].toDouble()).toFloat()
//        val z = Math.toDegrees(orientationCoordinates[0].toDouble()).toFloat()
//
//        return Coordinates(pitch, roll, z)
//    }
//
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