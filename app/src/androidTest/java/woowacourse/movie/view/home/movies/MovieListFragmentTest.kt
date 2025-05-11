package woowacourse.movie.view.home.movies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.MainActivity

class MovieListFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 영화_목록이_표시된다() {
        onView(withId(R.id.rv_movie_list)).check(matches(isDisplayed()))
    }

    @Test
    fun 영화_포스터가_표시된다() {
        onView(withId(R.id.rv_movie_list)).check(
            matches(
                hasDescendant(
                    allOf(
                        withId(R.id.img_poster),
                        isDisplayed(),
                    ),
                ),
            ),
        )
    }

    @Test
    fun 영화_제목이_표시된다() {
        onView(withId(R.id.rv_movie_list)).check(
            matches(
                hasDescendant(
                    allOf(
                        withId(R.id.tv_title),
                        withText("해리 포터와 마법사의 돌"),
                        isDisplayed(),
                    ),
                ),
            ),
        )
    }

    @Test
    fun 영화_러닝_타임이_표시된다() {
        onView(withId(R.id.rv_movie_list)).check(
            matches(
                hasDescendant(
                    allOf(
                        withId(R.id.tv_running_time),
                        withText("152분"),
                        isDisplayed(),
                    ),
                ),
            ),
        )
    }
}
