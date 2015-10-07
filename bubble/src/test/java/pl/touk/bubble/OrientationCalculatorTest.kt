package pl.touk.bubble

import org.junit.Test
import pl.touk.android.bubble.Coordinates
import pl.touk.android.bubble.OrientationCalculator
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.Orientation

class OrientationCalculatorTest {

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
        val MINUS_90 = (-Math.PI/2).toFloat()
        val MINUS_45 = (-Math.PI/4).toFloat()
        val ZERO = 0f
        val PLUS_45 = (Math.PI/4).toFloat()
    }

    val orientationCalculator: OrientationCalculator = OrientationCalculator()

    @Test
    public fun shouldReturnPortraitWhenPhoneInFullPortrait() {
        //given
        val coordinates = Coordinates(pitch = Degree.MINUS_90,
                                        roll = Degree.ZERO)

        //when
        val orientation = orientationCalculator.calculate(coordinates)

        //then
        assertThat(orientation).isEqualTo(Orientation.PORTRAIT)
    }

    @Test
    public fun shouldReturnLandscapeWhenPhoneInFullLandscape() {
        //given
        val coordinates = Coordinates(pitch = Degree.ZERO,
                                        roll = Degree.MINUS_90)

        //when
        val orientation = orientationCalculator.calculate(coordinates)

        //then
        assertThat(orientation).isEqualTo(Orientation.LANDSCAPE)
    }

    @Test
    // pitch == whatever & roll ==0
    public fun shouldReturnPortraitWhenPhoneLiesHorizontalWithFrontPointsTheSky() {
        //given
        val roll = Degree.ZERO

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(Degree.ZERO, roll)))
                .isEqualTo(Orientation.PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(Degree.MINUS_45, roll)))
                .isEqualTo(Orientation.PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(Degree.MINUS_90, roll)))
                .isEqualTo(Orientation.PORTRAIT)
    }

    @Test
    //pitch > -45 & roll <= -45
    public fun shouldReturnLandscapeWhenPhoneLiesOnTheLeftMoreThan45AndTiltedMoreThan45() {
        //given
        val coordinates = Coordinates(pitch = Degree.MINUS_45 + 1f,
                                        roll = Degree.MINUS_45 - 1f)

        //when
        val orientation = orientationCalculator.calculate(coordinates)

        //then
        assertThat(orientation).isEqualTo(Orientation.LANDSCAPE)
    }

    @Test
    //pitch > -45 & roll > -45 => Portrait
    public fun shouldReturnPortraitWhenPhoneLiesOnTheLeftMoreThan45AndTiltedLessThan45() {
        //given
        val coordinates = Coordinates(pitch = Degree.MINUS_45 + 1f,
                                        roll = Degree.MINUS_45 + 1f)

        //when
        val orientation = orientationCalculator.calculate(coordinates)

        //then
        assertThat(orientation).isEqualTo(Orientation.PORTRAIT)
    }

    @Test
    // pitch < -45 & roll == whatever
    public fun shouldReturnPortraitWhenPhonePitchLessThan45FromVertical() {
        //given
        val pitch = Degree.MINUS_45 - 1f

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.MINUS_90)))
            .isEqualTo(Orientation.PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.MINUS_45)))
                .isEqualTo(Orientation.PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.ZERO)))
                .isEqualTo(Orientation.PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.PLUS_45)))
                .isEqualTo(Orientation.PORTRAIT)
    }
}