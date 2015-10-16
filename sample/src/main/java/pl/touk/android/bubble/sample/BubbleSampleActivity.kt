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
