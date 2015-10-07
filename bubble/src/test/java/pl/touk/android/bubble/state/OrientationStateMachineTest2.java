package pl.touk.android.bubble.state;

import org.junit.Test;

import pl.touk.android.bubble.Coordinates;
import pl.touk.android.bubble.Orientation;
import pl.touk.android.bubble.OrientationCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrientationStateMachineTest2 {

    OrientationStateMachine orientationStateMachine = new OrientationStateMachine();

    @Test
    public void machineShouldStartWithUndefinedState() {
        //then
        assertThat(orientationStateMachine.getOrientation()).isEqualTo(Orientation.UNDEFINED);
    }

    @Test
    public void firstStateAfterUndefinedShouldBeFullyCalculated() {
        //given
        OrientationCalculator calculator = mock(OrientationCalculator.class);
        orientationStateMachine.setOrientationCalculator(calculator);
        Coordinates c = new Coordinates(0f, 0f);
        when(calculator.calculate(c)).thenReturn(Orientation.LANDSCAPE);

        //when
        orientationStateMachine.update(c);

        //then
        assertThat(orientationStateMachine.getOrientation()).isEqualTo(Orientation.LANDSCAPE);
    }


}
