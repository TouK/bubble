package pl.touk.android.bubble.state

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.orientation.OrientationCalculator
import org.mockito.Matchers.any
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import org.mockito.Mockito.mock
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachineTest {

    val orientationStateMachine = BubbleStateMachine()

    @Test
    public fun machineShouldStartWithUndefinedState() {
        //then
        assertThat(orientationStateMachine.orientation).isEqualTo(Orientation.UNDEFINED)
    }

    @Test
    public fun firstStateAfterUndefinedShouldBeFullyCalculated() {
        //given
        val calculator = mock(OrientationCalculator::class.java)
        orientationStateMachine.orientationCalculator = calculator
        val sampleCoordinates = Coordinates(0f, 0f)
        whenInvoke(calculator.calculate(sampleCoordinates)).thenReturn(Orientation.LANDSCAPE)

        //when
        orientationStateMachine.update(sampleCoordinates)

        //then
        assertThat(orientationStateMachine.orientation).isEqualTo(Orientation.LANDSCAPE)
    }

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
    public fun landscapeShouldNotChangeUntilPitchMoreThanMinus45Degree() {
        val moreThanMinus45 = Degree.MINUS_45 + Degree.ONE

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(moreThanMinus45, Degree.ZERO),
                expectedState  = Orientation.LANDSCAPE)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(moreThanMinus45, Degree.MINUS_45),
                expectedState  = Orientation.LANDSCAPE)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(moreThanMinus45, Degree.MINUS_45 + Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(moreThanMinus45, Degree.MINUS_45 - Degree.ONE),
                expectedState  = Orientation.LANDSCAPE)

        verifyStateChange(initState = Orientation.LANDSCAPE,
                newCoordinates = Coordinates(moreThanMinus45, Degree.MINUS_90),
                expectedState  = Orientation.LANDSCAPE)
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

    private fun verifyStateChange(initState: Orientation,
                                  newCoordinates: Coordinates,
                                  expectedState: Orientation) {
        //given
        orientationStateMachine.orientation = initState

        //when
        orientationStateMachine.update(newCoordinates)

        //then
        assertThat(orientationStateMachine.orientation).isEqualTo(expectedState)
    }

}