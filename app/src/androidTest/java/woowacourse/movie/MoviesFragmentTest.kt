package woowacourse.movie

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.jupiter.api.Test
import woowacourse.movie.view.movie.MoviesFragment

class MoviesFragmentTest {
    @Test
    fun `영화목록이_화면에_보여야_한다`() {
        launchFragmentInContainer { MoviesFragment() }

        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }
}
