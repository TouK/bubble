package pl.touk.android.bubble.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import pl.touk.android.bubble.Bubble
import pl.touk.android.bubble.BubbleEvent
import pl.touk.android.bubble.sample.R

class BubbleSampleActivity : AppCompatActivity() {

    val bubble: Bubble = Bubble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_sample)
        val textView = findViewById(R.id.label) as TextView
        bubble.register(this)
            .subscribe { bubbleEvent: BubbleEvent -> textView.text = bubbleEvent.orientation.name() }


    }
}
