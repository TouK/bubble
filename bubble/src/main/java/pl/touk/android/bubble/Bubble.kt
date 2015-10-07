package pl.touk.android.bubble

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import rx.Observable
import rx.subjects.PublishSubject

public class Bubble: SensorEventListener {

    companion object {
        private val PITCH_POSITION = 1
        private val ROLL_POSITION = 2
        private val SAMPLE_SIZE = 20
    }

    lateinit var accelerometerSensor: Sensor
    lateinit var magneticSensor: Sensor
    lateinit var sensorManager: SensorManager

    var orientationPublisher: PublishSubject<BubbleEvent>? = null
    private var coordinatesPublisher: PublishSubject<Coordinates>? = null

    private val orientationCalculator = OrientationCalculator()

    private val magneticSensorValues: FloatArray = FloatArray(3)
    private val accelerometerSensorValues: FloatArray = FloatArray(3)
    private val rotationMatrix: FloatArray = FloatArray(9)
    private val orientationCoordinates: FloatArray = FloatArray(3)

    var lastOrientation = Orientation.PORTRAIT

    public fun register(context: Context): Observable<BubbleEvent> {
        loadSensosrs(context)

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)
        orientationPublisher = PublishSubject.create()
        coordinatesPublisher = PublishSubject.create()
        coordinatesPublisher!!
                .buffer(SAMPLE_SIZE)
                .map { coordinates: List<Coordinates> -> averageCoordinates(coordinates) }
                .subscribe { coordinates: Coordinates ->
                    orientationPublisher!!.onNext(BubbleEvent(orientationCalculator.calculate(coordinates)))
                }
        return orientationPublisher!!
    }

    private fun averageCoordinates(coordinates: List<Coordinates>): Coordinates {
        var averagePitch = 0f
        var averageRoll = 0f
        val size = coordinates.size().toFloat()

        coordinates.forEach {
            averagePitch += it.pitch
            averageRoll += it.roll
        }

        return Coordinates(averagePitch/size, averageRoll/size)
    }

    private fun loadSensosrs(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    public fun unregister() {
        sensorManager.unregisterListener(this)
        ifRegistered(orientationPublisher) {
            coordinatesPublisher!!.onCompleted()
            orientationPublisher!!.onCompleted()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        cacheEventData(sensorEvent)
        coordinatesPublisher!!.onNext(calculateOrientationCoordinates())
    }

    private fun cacheEventData(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerSensorValues, 0, 3);
        } else if (sensorEvent.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magneticSensorValues, 0, 3);
        }
    }

    private fun calculateOrientationCoordinates(): Coordinates {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerSensorValues, magneticSensorValues);
        SensorManager.getOrientation(rotationMatrix, orientationCoordinates);

        val pitch = orientationCoordinates[PITCH_POSITION]
        val roll = orientationCoordinates[ROLL_POSITION]

        return Coordinates(pitch, roll)

    }

}

inline fun<T> ifRegistered(subject: PublishSubject<T>?, action: () -> Unit) {
     if (subject != null) {
         action.invoke()
     } else {
         throw IllegalStateException("Detector must be registered before use.")
     }
}