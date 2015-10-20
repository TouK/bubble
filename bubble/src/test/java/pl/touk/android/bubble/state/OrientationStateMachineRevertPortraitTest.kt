package pl.touk.android.bubble.state

import org.junit.Test
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachineReversePortraitTest: OrientationStateTestBase() {

    @Test
    public fun `P >=45 => Rev Portrait`() {
        val moreThan45 = Degree.PLUS_45 + Degree.ONE

        verifyStateChange(initState      = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(moreThan45, 0f),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState      = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(moreThan45, Degree.MINUS_45),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState      = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(moreThan45, Degree.MINUS_90),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState      = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(moreThan45, Degree.ZERO - Degree.ONE),
                          expectedState  = Orientation.REVERSE_PORTRAIT)
    }

    @Test
    public fun `[45 >= P >= -45] && [45 >= R >= -45] => Rev Portrait`() {
        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(Degree.ZERO, Degree.ZERO),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, moreThanMinus45),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, moreThanMinus45),
                          expectedState  = Orientation.REVERSE_PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                          newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, Degree.PLUS_45 - Degree.ONE),
                          expectedState  = Orientation.REVERSE_PORTRAIT)
    }

    @Test
    public fun `[45 >= P >= -45] && [-45 > R ] => Landscape`() {
        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE);

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(Degree.ZERO, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)
    }

    @Test
    public fun `[45 >= P >= -45] && [ R > 45 ] => Rev Landscape`() {
        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.PLUS_45 + Degree.ONE),
                expectedState  = Orientation.REVERSE_LANDSCAPE)

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(Degree.ZERO, Degree.PLUS_45 + Degree.ONE),
                expectedState  = Orientation.REVERSE_LANDSCAPE)
    }

    @Test
    public fun `[ -45 > P] && [ 45 > R > -45 ] => Rev Portrait`() {
        val belowMinus45 = Degree.MINUS_45 - Degree.ONE

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(belowMinus45, Degree.PLUS_45 - Degree.ONE),
                expectedState  = Orientation.PORTRAIT)

        verifyStateChange(initState = Orientation.REVERSE_PORTRAIT,
                newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45 + Degree.ONE),
                expectedState  = Orientation.PORTRAIT)
    }
}