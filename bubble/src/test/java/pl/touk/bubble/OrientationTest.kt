package pl.touk.bubble

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import pl.touk.android.bubble.Orientation
import rx.Observable

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


    @Test
    public fun sad() {
        Observable.from(listOf(1, 2, 3, 4, 5))
                .buffer(2)
                .map { it.average() }
                .subscribe {
                    System.out.println(it)
                }
    }
}