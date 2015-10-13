package pl.touk.android.bubble

import org.junit.Test
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.orientation.OrientationCalculator
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.testvalue.Degree

class OrientationCalculatorTest {

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

    @Test
    // pitch > 45 & roll == whatever => REVESE_PORTRAIT
    public fun shouldReturnReversePortraitWhenPhonePitchLessThan45FromReverseVertical() {
        //given
        val pitch = Degree.PLUS_45 + 1f

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.MINUS_90)))
            .isEqualTo(Orientation.REVERSE_PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.MINUS_45)))
                .isEqualTo(Orientation.REVERSE_PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.ZERO)))
                .isEqualTo(Orientation.REVERSE_PORTRAIT)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch, roll = Degree.PLUS_45)))
                .isEqualTo(Orientation.REVERSE_PORTRAIT)
    }

    @Test
    // -45 < pitch < 45 & roll >= 45 => REVESE_LANDSCAPE
    public fun shouldReturnReverseLandscapeWhenPhoneRollMoreThan45AndPitchInRangeMinus45And45() {
        //given
        val roll = Degree.PLUS_45 + 1f

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch = Degree.PLUS_45 - Degree.ONE,
                                                                roll = roll)))
            .isEqualTo(Orientation.REVERSE_LANDSCAPE)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch = Degree.ZERO,
                                                                roll = roll)))
                .isEqualTo(Orientation.REVERSE_LANDSCAPE)

        //when then
        assertThat(orientationCalculator.calculate(Coordinates(pitch = Degree.MINUS_45 + Degree.ONE,
                                                                roll = roll)))
                .isEqualTo(Orientation.REVERSE_LANDSCAPE)
    }
}