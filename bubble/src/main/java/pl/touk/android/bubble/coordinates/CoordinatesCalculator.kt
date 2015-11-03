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

    private val magneticSensorValues: FloatArray = FloatArray(SENSOR_RESULTS_SIZE)
    private val accelerometerSensorValues: FloatArray = FloatArray(SENSOR_RESULTS_SIZE)
    private val rotationMatrix: FloatArray = FloatArray(ROTATION_MATRIX_SIZE)
    private val orientationCoordinates: FloatArray = FloatArray(ORIENTATION_COORDINATES_RESULT_SIZE)

    public fun calculate(sensorEvent: SensorEvent): Coordinates {
        cacheEventData(sensorEvent)
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerSensorValues, magneticSensorValues);
        SensorManager.getOrientation(rotationMatrix, orientationCoordinates);

        val pitch = orientationCoordinates[PITCH_POSITION]
        val roll = orientationCoordinates[ROLL_POSITION]

        return Coordinates(pitch, roll)
    }

    private fun cacheEventData(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerSensorValues, 0, SENSOR_RESULTS_SIZE);
        } else if (sensorEvent.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magneticSensorValues, 0, SENSOR_RESULTS_SIZE);
        }
    }

    internal fun calculateAverage(coordinates: List<Coordinates>): Coordinates {
        var averagePitch = 0f
        var averageRoll = 0f
        val size = coordinates.size().toFloat()

        coordinates.forEach {
            averagePitch += it.pitch
            averageRoll += it.roll
        }

        return Coordinates(averagePitch / size, averageRoll / size)
    }
}