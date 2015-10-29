package pl.touk.android.bubble.state

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.orientation.Orientation
import pl.touk.android.bubble.orientation.OrientationCalculator
import pl.touk.android.bubble.coordinates.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import org.mockito.Mockito.mock
import pl.touk.android.bubble.testvalue.Degree


class OrientationStateMachineTest: OrientationStateTestBase() {

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
}