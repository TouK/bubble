package pl.touk.bubble

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class OrientationTest {

    @Test
    public fun shouldDetectLandscapeForFullLandscapeOrientation() {
        //given
        val xFullLandscapeValue = 0f
        val yFullLandscapeValue = -1.5f
        //when & then

        assertThat(true).isFalse()
    }
}