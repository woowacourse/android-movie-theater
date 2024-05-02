package woowacourse.movie


import MovieAdapter
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.movieList.MovieListFragment
import java.time.LocalDate


@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    private val movie = MovieInfo(
        Title("차람과 하디의 진지한 여행기 1"),
        MovieDate(LocalDate.of(2024, 2, 25)),
        RunningTime(230),
        Synopsis("wow!"),
    )


    @Test
    @DisplayName("영화 목록이 화면에 보여지는지 테스트")
    fun test1() {
        launchFragmentInContainer<MovieListFragment>()

        val viewInteraction = onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                ViewMatchers.hasDescendant(
                    withText(
                        movie.title.toString(),
                    ),
                ),
            ).atPosition(0),
        )

        // then : 해당 View 가 screenDate 와 runningTime 을 가지고 있는지 확인
        viewInteraction.check(matches(ViewMatchers.hasDescendant(withText(movie.title.toString()))))
    }

    @Test
    @DisplayName("영화 버튼 누르면 Dialog 보이는지 테스트")
    fun test2() {
        launchFragmentInContainer<MovieListFragment>()
        onView(withId(R.id.rv_movies))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(
                        0,
                        clickItemWithId(R.id.movie_details_button)
                    )
            )

        onView(withId(R.id.rv_cinema)).check(matches(isDisplayed()))
    }


    // reference: https://www.maskaravivek.com/post/working-with-recycler-views-in-espresso-tests/
    private fun clickItemWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as View
                v.performClick()
            }
        }
    }
}