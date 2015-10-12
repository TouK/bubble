package pl.touk.android.bubble

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.coordinates.CoordinatesCalculator
import pl.touk.android.bubble.listener.BubbleListener
import pl.touk.android.bubble.orientation.Orientation
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
    lateinit private var coordinatesPublisher: PublishSubject<Coordinates>

    private val orientationStateMachine = BubbleStateMachine()
    private val coordinatesCalculator = CoordinatesCalculator()

    enum class Registration { UNREGISTERED, LISTENER, OBSERVER }
    var registration = Registration.UNREGISTERED
    var bubbleListener: BubbleListener? = null

    public fun register(context: Context): Observable<BubbleEvent> {
        orientationPublisher = PublishSubject.create()
        registration = Registration.OBSERVER

        setupAndRegisterSensorsListeners(context)
        return orientationPublisher!!
    }

    public fun register(bubbleListener: BubbleListener, context: Context) {
        this.bubbleListener = bubbleListener
        registration = Registration.LISTENER

        setupAndRegisterSensorsListeners(context)
    }

    private fun setupAndRegisterSensorsListeners(context: Context) {
        coordinatesPublisher = PublishSubject.create()
        coordinatesPublisher
                .buffer(SAMPLE_SIZE)
                .map { coordinates: List<Coordinates> -> coordinatesCalculator.calculateAverage(coordinates) }
                .subscribe { coordinates: Coordinates ->
                    if (orientationStateMachine.update(coordinates)) {
                        informClient(orientationStateMachine.orientation)
                    }
                }

        loadSensors(context)
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)
    }

    private fun informClient(orientation: Orientation) {
        if (registration == Registration.OBSERVER) {
            orientationPublisher!!.onNext(BubbleEvent(orientation))
        } else {
            bubbleListener!!.onOrientationChanged(BubbleEvent(orientation))
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        coordinatesPublisher.onNext(coordinatesCalculator.calculate(sensorEvent))
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun loadSensors(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    public fun unregister() {
        sensorManager.unregisterListener(this)
        ifRegistered() {
            coordinatesPublisher.onCompleted()
            if (registration == Registration.OBSERVER) {
                orientationPublisher!!.onCompleted()
            } else {
                bubbleListener = null
            }
        }
    }

    inline fun ifRegistered(action: () -> Unit) {
        if (registration != Registration.UNREGISTERED) {
            action.invoke()
        } else {
            throw IllegalStateException("Detector must be registered before use.")
        }
    }

}