package pl.touk.android.bubble

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.coordinates.CoordinatesCalculator
import pl.touk.android.bubble.state.BubbleStateMachine
import rx.Observable
import rx.subjects.PublishSubject

public class Bubble: SensorEventListener {

    companion object {
        private val SAMPLE_SIZE = 20
    }

    lateinit var accelerometerSensor: Sensor
    lateinit var magneticSensor: Sensor
    lateinit var sensorManager: SensorManager

    var orientationPublisher: PublishSubject<BubbleEvent>? = null
    private var coordinatesPublisher: PublishSubject<Coordinates>? = null

    private val orientationStateMachine = BubbleStateMachine()
    private val coordinatesCalculator = CoordinatesCalculator()

    public fun register(context: Context): Observable<BubbleEvent> {

        orientationPublisher = PublishSubject.create()
        coordinatesPublisher = PublishSubject.create()
        coordinatesPublisher!!
                .buffer(SAMPLE_SIZE)
                .map { coordinates: List<Coordinates> -> coordinatesCalculator.averageCoordinates(coordinates) }
                .subscribe { coordinates: Coordinates ->
                    if (orientationStateMachine.update(coordinates)) {
                        orientationPublisher!!.onNext(BubbleEvent(orientationStateMachine.orientation))
                    }
                }

        loadSensors(context)
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)

        return orientationPublisher!!
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        coordinatesPublisher!!.onNext(coordinatesCalculator.calculate(sensorEvent))
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun loadSensors(context: Context) {
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
}

inline fun<T> ifRegistered(subject: PublishSubject<T>?, action: () -> Unit) {
     if (subject != null) {
         action.invoke()
     } else {
         throw IllegalStateException("Detector must be registered before use.")
     }
}