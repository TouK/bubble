package pl.touk.android.bubble.state

import org.assertj.core.api.Assertions
import pl.touk.android.bubble.coordinates.Coordinates
import pl.touk.android.bubble.orientation.Orientation


open class OrientationStateTestBase {

    val orientationStateMachine = BubbleStateMachine()


    fun verifyStateChange(initState: Orientation,
                                  newCoordinates: Coordinates,
                                  expectedState: Orientation) {
        //given
        orientationStateMachine.orientation = initState

        //when
        orientationStateMachine.update(newCoordinates)

        //then
        Assertions.assertThat(orientationStateMachine.orientation).isEqualTo(expectedState)
    }
}