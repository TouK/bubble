package pl.touk.android.bubble.state

import org.junit.Test
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachineReverseLandscapeTest: OrientationStateTestBase() {

    @Test
    // [45 >= P >= -45] && [45 >= R >= -45] => Rev Landscape
    public fun landscapeShouldNotChangeUntilPitchInRangeMinus45_45AndRollInRangeMinus45_45() {
        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.ZERO, Degree.ZERO),
                expectedState  = Orientation.REVERSE_LANDSCAPE)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, moreThanMinus45),
                expectedState  = Orientation.REVERSE_LANDSCAPE)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, moreThanMinus45),
                expectedState  = Orientation.REVERSE_LANDSCAPE)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, Degree.PLUS_45 - Degree.ONE),
                expectedState  = Orientation.REVERSE_LANDSCAPE)
    }

    @Test
    // [-45 > P ]=> Portrait
    public fun landscapeShouldChangeToPortraitWhenPitchBelowMinus45Degree() {
        val belowMinus45 = Degree.MINUS_45 - Degree.ONE

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.ZERO),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45 + Degree.ONE),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_90),
                expectedState  = Orientation.PORTRAIT)
    }

    @Test
    // [45 >= P >= -45] && [ -45 > R ] => Landscape
    public fun portraitShouldChangeToReverseLandscapeWhenPitchInRangeMinus45_45AndRollMoreThan45() {
        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(Degree.ZERO, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)
    }

    @Test
    // [ P > 45] => Rev Portrait
    public fun portraitShouldChangeToReversePortraitWhenPitchMoreThan45AndRollInRangeMinus45_45() {
        val moreThan45 = Degree.PLUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(moreThan45, Degree.PLUS_45 - Degree.ONE),
                expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_LANDSCAPE,
                newCoordinates = Coordinates(moreThan45, Degree.MINUS_45 + Degree.ONE),
                expectedState  = Orientation.REVERSE_PORTRAIT)
    }
}