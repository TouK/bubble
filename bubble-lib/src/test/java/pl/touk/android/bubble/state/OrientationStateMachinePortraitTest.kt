package pl.touk.android.bubble.state

import org.junit.Test
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachinePortraitTest: OrientationStateTestBase() {

//    @Test
//    public fun `-45 ge P gives Portrait`() {
//        val belowMinus45 = Degree.MINUS_45 - Degree.ONE
//
//        verifyStateChange(initState      = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(belowMinus45, 0f),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState      = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(belowMinus45, Degree.MINUS_45),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState      = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(belowMinus45, Degree.MINUS_90),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState      = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(belowMinus45, Degree.ZERO - Degree.ONE),
//                          expectedState  = Orientation.PORTRAIT)
//    }
//
//    @Test
//    public fun `'45 ge P ge -45' and '45 ge R ge -45' gives Portrait`() {
//        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(Degree.ZERO, Degree.ZERO),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, moreThanMinus45),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, moreThanMinus45),
//                          expectedState  = Orientation.PORTRAIT)
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                          newCoordinates = Coordinates(Degree.PLUS_45 - Degree.ONE, Degree.PLUS_45 - Degree.ONE),
//                          expectedState  = Orientation.PORTRAIT)
//    }
//
//    @Test
//    public fun `'45 ge P ge -45' and '-45 gt R ' gives Landscape`() {
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.MINUS_45 - Degree.ONE),
//                expectedState  = Orientation.LANDSCAPE);
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(Degree.ZERO, Degree.MINUS_45 - Degree.ONE),
//                expectedState  = Orientation.LANDSCAPE)
//    }
//
//    @Test
//    public fun `'45 ge P ge -45' and ' R gt 45 ' gives Rev Landscape`() {
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(Degree.MINUS_45 + Degree.ONE, Degree.PLUS_45 + Degree.ONE),
//                expectedState  = Orientation.REVERSE_LANDSCAPE)
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(Degree.ZERO, Degree.PLUS_45 + Degree.ONE),
//                expectedState  = Orientation.REVERSE_LANDSCAPE)
//    }
//
//    @Test
//    public fun `' P gt 45' and ' 45 gt R gt -45 ' gives Rev Portrait`() {
//        val moreThan45 = Degree.PLUS_45 + Degree.ONE
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(moreThan45, Degree.PLUS_45 - Degree.ONE),
//                expectedState  = Orientation.REVERSE_PORTRAIT)
//
//        verifyStateChange(initState = Orientation.PORTRAIT,
//                newCoordinates = Coordinates(moreThan45, Degree.MINUS_45 + Degree.ONE),
//                expectedState  = Orientation.REVERSE_PORTRAIT)
//    }
}