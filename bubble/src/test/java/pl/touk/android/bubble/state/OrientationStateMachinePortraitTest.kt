package pl.touk.android.bubble.state

import org.junit.Test
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachinePortraitTest: OrientationStateTestBase() {

    @Test
    public fun portraitShouldNotChangeUntilPithBelowMinus45Degree() {
        val belowMinus45 = Degree.MINUS_45 - Degree.ONE

        verifyStateChange(initState      = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(belowMinus45, 0f),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState      = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState      = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(belowMinus45, Degree.MINUS_90),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState      = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(belowMinus45, Degree.ZERO - Degree.ONE),
                          expectedState  = Orientation.PORTRAIT)
    }

    @Test
    public fun portraitShouldNotChangeUntilRollMoreThanMinus45Degree() {
        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(Degree.ZERO, moreThanMinus45),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, moreThanMinus45),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(Degree.MINUS_45 - Degree.ONE, moreThanMinus45),
                          expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                          newCoordinates = Coordinates(Degree.MINUS_90 + Degree.ONE, moreThanMinus45),
                          expectedState  = Orientation.PORTRAIT)
    }

    @Test
    public fun portraitShouldChangeToLandscapeWhenPitchMoreThanMinus45AndRollBelowMinus45() {
        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)
    }

    @Test
    public fun portraitShouldChangeToReverseLandscapeWhenPitchInRangeMinus45_45AndRollMoreThan45() {
        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.PLUS_45 + Degree.ONE),
                expectedState  = Orientation.REVERSE_LANDSCAPE)
    }
}