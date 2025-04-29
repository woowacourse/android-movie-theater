package woowacourse.movie.presentation.view.movies

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test
import woowacourse.movie.R

class MoviesFragmentTest {
    @Test
    fun `영화목록이_보여진다`() {
        launchFragmentInContainer { MoviesFragment() }

        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
    }
}
