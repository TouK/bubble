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
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

public class Bubble: SensorEventListener {

    companion object {
        private val SAMPLE_SIZE = 20
    }
    enum class Registration { UNREGISTERED, LISTENER, OBSERVER }

    var orientationPublisher: PublishSubject<BubbleEvent>? = null
    var bubbleListener: BubbleListener? = null


    lateinit private var accelerometerSensor: Sensor
    lateinit private var magneticSensor: Sensor
    lateinit private var sensorManager: SensorManager
    lateinit private var coordinatesPublisher: PublishSubject<Coordinates>
    private val orientationStateMachine = BubbleStateMachine()
    private val coordinatesCalculator = CoordinatesCalculator()

    var registration = Registration.UNREGISTERED

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
                    orientationStateMachine.update(coordinates)
                    informClient(orientationStateMachine.orientation, coordinates)
                }

        loadSensors(context)
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)
    }

    private fun informClient(orientation: Orientation, coordinates: Coordinates) {
        if (registration == Registration.OBSERVER) {
            orientationPublisher!!.onNext(BubbleEvent(orientation, coordinates))
        } else {
            bubbleListener!!.onOrientationChanged(BubbleEvent(orientation, coordinates))
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
            coordinatesPublisher.onComplete()
            if (registration == Registration.OBSERVER) {
                orientationPublisher!!.onComplete()
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