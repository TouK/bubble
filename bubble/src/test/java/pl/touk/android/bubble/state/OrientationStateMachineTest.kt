package pl.touk.android.bubble.state

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.Orientation
import pl.touk.android.bubble.OrientationCalculator
import org.mockito.Matchers.any
import pl.touk.android.bubble.Coordinates
import org.mockito.Mockito.`when` as whenInvoke
import org.mockito.Mockito.mock


class OrientationStateMachineTest {

    val orientationStateMachine = OrientationStateMachine()

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