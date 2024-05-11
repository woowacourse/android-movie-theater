package woowacourse.movie.java.movie.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.list.view.HomeActivity

@RunWith(AndroidJUnit4::class)
class MovieListTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun `영화_목록을_볼_수_있다`() {
        onView(withId(R.id.home_fragment_item)).perform(click())
        onView(withId(R.id.movie_recycler_view)).check(matches(isDisplayed()))
    }
}
