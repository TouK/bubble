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
import pl.touk.android.bubble.orientation.Orientation

class BubbleSampleActivity : AppCompatActivity() {

    val ANIMATION_DURATION: Long = 500

    val bubble: Bubble = Bubble()
    lateinit var textView: TextView
    var startAngle = 0f
    var endAngle = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sample)
        textView = findViewById(R.id.label) as TextView
        bubble.register(this)
            .subscribe { bubbleEvent: BubbleEvent -> rotateTo(bubbleEvent.orientation) }
    }

    private fun rotateTo(orientation: Orientation) {
        Log.e("Sample", "Rotate to: $orientation")
        val animSet = AnimationSet(true)
        animSet.interpolator = DecelerateInterpolator()
        animSet.fillAfter = true
        animSet.isFillEnabled = true

        val animRotate = RotateAnimation(startAngle, extractEndAngle(orientation),
                                            Animation.RELATIVE_TO_SELF, 0.5f,
                                            Animation.RELATIVE_TO_SELF, 0.5f)

        animRotate.duration = ANIMATION_DURATION
        animRotate.fillAfter = true
        animSet.addAnimation(animRotate)
        startAngle = extractEndAngle(orientation)
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
}
