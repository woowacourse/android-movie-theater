package woowacourse.movie.presentation.view.navigation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.view.screening.ScreeningMovieFragment
import woowacourse.movie.presentation.view.screening.adapter.MovieViewHolder

@RunWith(AndroidJUnit4::class)
class ScreeningMovieFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.selected_fragment,
                ScreeningMovieFragment(),
            ).commit()
        }
    }

    @Test
    fun `영화_목록이_화면에_표시된다`() {
        onView(withId(R.id.movieList))
            .check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `영화_아이템이_여러개로_표시된다`() {
        repeat(4) { idx ->
            onView(withId(R.id.movieList))
                .perform(
                    RecyclerViewActions.scrollToHolder(
                        instanceOf(MovieViewHolder::class.java),
                    ).atPosition(idx),
                )
        }
    }
}
