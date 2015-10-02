package pl.touk.android.bubble.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import pl.touk.bubble.Bubble

class BubbleSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bubble = Bubble();
        setContentView(R.layout.activity_bubble_sample)
        val textView = findViewById(R.id.label) as TextView
        textView.setText(bubble.register())
    }
}
