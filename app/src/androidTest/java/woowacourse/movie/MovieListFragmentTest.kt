package woowacourse.movie

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.movie.presentation.home.HomeActivity
import woowacourse.movie.presentation.homefragments.movieList.MovieListFragment

@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun `영화_목록이_화면에_표시된다`() {
        val scenario = launchFragmentInContainer<MovieListFragment>()

        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }
}
