package pl.touk.android.bubble.listener

import pl.touk.android.bubble.BubbleEvent


interface BubbleListener {

    fun onOrientationChanged(bubble: BubbleEvent)
}