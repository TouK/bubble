package pl.touk.bubble

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.Orientation

class OrientationTest {

    @Test
    public fun shouldDetectLandscapeForFullLandscapeOrientation() {
        //given
        val xFullLandscapeValue = 0f
        val yFullLandscapeValue = -1.5f
        //when & then

        assertThat(Orientation.extract(xFullLandscapeValue, yFullLandscapeValue))
                .isEqualTo(Orientation.LANDSCAPE)
    }

    @Test
    public fun shouldDetectPortraitForFullPortraitOrientation() {
        //given
        val xFullPortraitValue = -1.5f
        val yFullPortraitValue = 0f
        //when & then

        assertThat(Orientation.extract(xFullPortraitValue, yFullPortraitValue))
                .isEqualTo(Orientation.PORTRAIT)
    }
}