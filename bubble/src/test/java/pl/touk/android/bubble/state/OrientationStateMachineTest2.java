package pl.touk.android.bubble.state;

import org.junit.Test;

import pl.touk.android.bubble.coordinates.Coordinates;
import pl.touk.android.bubble.Orientation;
import pl.touk.android.bubble.orientation.OrientationCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrientationStateMachineTest2 {

    BubbleStateMachine bubbleStateMachine = new BubbleStateMachine();

    @Test
    public void machineShouldStartWithUndefinedState() {
        //then
        assertThat(bubbleStateMachine.getOrientation()).isEqualTo(Orientation.UNDEFINED);
    }

    @Test
    public void firstStateAfterUndefinedShouldBeFullyCalculated() {
        //given
        OrientationCalculator calculator = mock(OrientationCalculator.class);
        bubbleStateMachine.setOrientationCalculator(calculator);
        Coordinates c = new Coordinates(0f, 0f);
        when(calculator.calculate(c)).thenReturn(Orientation.LANDSCAPE);

        //when
        bubbleStateMachine.update(c);

        //then
        assertThat(bubbleStateMachine.getOrientation()).isEqualTo(Orientation.LANDSCAPE);
    }


}
