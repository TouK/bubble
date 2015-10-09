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