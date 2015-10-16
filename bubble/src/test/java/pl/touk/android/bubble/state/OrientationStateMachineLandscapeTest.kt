package pl.touk.android.bubble.state

import org.junit.Test
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachineLandscapeTest: OrientationStateTestBase() {

    @Test
    // [45 >= P >= -45] && [45 >= R >= -45] => Landscape
    public fun landscapeShouldNotChangeUntilPitchInRangeMinus45_45AndRollInRangeMinus45_45() {
        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.ZERO, Degree.ZERO),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, moreThanMinus45),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, moreThanMinus45),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.PORTRAIT,
                newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, Degree.PLUS_45 - Degree.ONE),
                expectedState  = Orientation.PORTRAIT)
    }

    @Test
    public fun landscapeShouldChangeToPortraitWhenPitchBelowMinus45Degree() {
        val belowMinus45 = Degree.MINUS_45 - Degree.ONE

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.ZERO),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45 + Degree.ONE),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_90),
                expectedState  = Orientation.PORTRAIT)
    }
}