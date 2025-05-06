package woowacourse.movie.feature

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.feature.home.view.HomeFragment
import woowacourse.movie.nthChildOf

@Suppress("ktlint:standard:function-naming")
class HomeFragmentTest {
    @Before
    fun setup() {
        launchFragmentInContainer<HomeFragment>()
    }

    @Test
    fun `화면에_영화_제목이_출력된다`() {
        onView(
            allOf(
                withId(R.id.tv_movie_title),
                isDescendantOfA(nthChildOf(withId(R.id.rv_home_movies), 0)),
            ),
        ).check(matches(withText("해리 포터와 마법사의 돌")))
    }
}
