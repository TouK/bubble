package pl.touk.android.bubble.testvalue


class Degree {

    /*
     * http://www.jdroid.ch/grundelemente/bilder/sensorwerte.png
     * pitch    -90 -> full vertical (up of the phone points the sky)
     *          0   -> full horizontal
     *          90  -> full reverse vertical (up of the phone points the ground)
     *
     * roll     0           -> front oh the phone points the sky
     *          90          -> front oh the phone points the right
     *          -90         -> front oh the phone points the left
     *          -180/180    -> front oh the phone points the ground
     */

    companion object Degree {
        val MINUS_90 = (-Math.PI/2f).toFloat()
        val MINUS_45 = (-Math.PI/4f).toFloat()
        val ZERO = 0f
        val ONE = (Math.PI/180f).toFloat()
        val PLUS_45 = (Math.PI/4f).toFloat()
    }
}