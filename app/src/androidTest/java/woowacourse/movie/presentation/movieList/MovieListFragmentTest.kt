package woowacourse.movie.presentation.movieList

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.movieInfo

@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    @Test
    @DisplayName("영화 목록이 화면에 보여지는지 테스트")
    fun test1() {
        // given
        val movie = movieInfo()
        val title = movie.title.name
        // when
        launchFragmentInContainer<MovieListFragment>()

        val viewInteraction =
            onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.scrollToHolder(
                    CoreMatchers.instanceOf(MovieAdapter.MovieViewHolder::class.java),
                ).atPosition(0),
            )
        viewInteraction.check(matches(hasDescendant(withText(title))))
    }
}
