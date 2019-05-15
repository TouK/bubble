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

package pl.touk.android.bubble.sample

import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import pl.touk.android.bubble.Bubble
import pl.touk.android.bubble.BubbleEvent
import pl.touk.android.bubble.BubbleSettings
import pl.touk.android.bubble.bookkeeper.BookKeeper
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.orientation.Orientation

class BubbleSampleActivity : AppCompatActivity() {

    val ANIMATION_DURATION: Long = 500

    val bubble: Bubble = Bubble(1, BubbleSettings(SensorManager.SENSOR_DELAY_FASTEST))
    lateinit var textView: TextView
    var startAngle = 0f
    var endAngle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sample)
        textView = findViewById(R.id.label) as TextView
        bubble.register(this)
            .subscribe { bubbleEvent: BubbleEvent -> rotateTo(bubbleEvent.orientation, bubbleEvent.coordinates) }
    }

    private val data = Array(1000) { 0L }
    private val rollData = Array(1000) { 0.0F }
    private val pitchData = Array(1000) { 0.0F }
    private var eventsCount = 0
    private var last = System.currentTimeMillis()
    private val bookKeeper = BookKeeper()

    private fun rotateTo(orientation: Orientation, coordinates: Coordinates) {

        if (eventsCount == 1000) {
            Log.i("BubbleAppData", "time ${bookKeeper.calculate(data)}")
            Log.i("BubbleAppData", "\troll ${bookKeeper.calculateF(rollData)}")
            Log.i("BubbleAppData", "\tpitch ${bookKeeper.calculateF(pitchData)}")
            eventsCount = 0
        }
        data[eventsCount] = System.currentTimeMillis() - last
        rollData[eventsCount] = coordinates.roll
        pitchData[eventsCount] = coordinates.pitch
        last = System.currentTimeMillis()
        ++eventsCount

        Log.e("Sample", "Rotate to: $orientation")
        val animSet = AnimationSet(true)
        animSet.interpolator = DecelerateInterpolator()
        animSet.fillAfter = true
        animSet.isFillEnabled = true

        val endAngle = extractEndAngle(coordinates.roll)

        val animRotate = RotateAnimation(startAngle, endAngle,
                                            Animation.RELATIVE_TO_SELF, 0.5f,
                                            Animation.RELATIVE_TO_SELF, 0.5f)

        animRotate.duration = ANIMATION_DURATION
        animRotate.fillAfter = true
        animSet.addAnimation(animRotate)
        startAngle = endAngle
//        textView.text = "${orientation.name}\n${coordinates.roll*180/3.1415}\n${coordinates.pitch*180/3.1415}"
        textView.text = "${orientation.name}\nroll: ${coordinates.roll}\npitch: ${coordinates.pitch}\n" +
                "azimuth: ${coordinates.z}"
        textView.startAnimation(animSet)
    }

    private fun extractEndAngle(orientation: Orientation): Float {
        return when(orientation) {
            Orientation.PORTRAIT -> 0f
            Orientation.LANDSCAPE -> 90f
            Orientation.REVERSE_LANDSCAPE -> 270f
            Orientation.REVERSE_PORTRAIT -> 180f
            else -> endAngle
        }
    }

    private fun extractEndAngle(roll: Float): Float {
        return -roll
    }
}
