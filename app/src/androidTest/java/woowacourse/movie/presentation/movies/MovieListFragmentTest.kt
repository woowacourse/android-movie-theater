package woowacourse.movie.presentation.movies

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
@Suppress("ktlint:standard:function-naming")
class MovieListFragmentTest {
    @Test
    fun 영화_목록이_출력된다() {
        launchFragmentInContainer<MovieListFragment>()

        onView(withId(R.id.recyclerview_movies))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))

        onView(withText("승부"))
            .check(matches(isDisplayed()))
    }
}
