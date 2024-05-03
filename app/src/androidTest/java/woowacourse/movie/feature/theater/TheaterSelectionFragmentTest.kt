package woowacourse.movie.feature.theater

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.TestFixture.FIRST_MOVIE_ITEM_POSITION
import woowacourse.movie.TestFixture.makeTheater
import woowacourse.movie.feature.home.HomeFragment
import woowacourse.movie.feature.theater.adapter.TheaterSelectionViewHolder
import woowacourse.movie.utils.MovieUtils.convertScreeningInfoFormat

@RunWith(AndroidJUnit4::class)
class TheaterSelectionFragmentTest {
    @Before
    fun setUp() {
        val movieId = bundleOf(HomeFragment.MOVIE_ID to FIRST_ITEM_POSITION)
        launchFragmentInContainer<TheaterSelectionFragment>(movieId)
    }

    @Test
    fun `극장_목록을_보여준다`() {
        onView(withId(R.id.recycler_view_theater_selection)).check(matches(isDisplayed()))
    }

    @Test
    fun `극장_목록을_스크롤했을_때_첫_번째_위치의_TheaterSelectionViewHolder는_극장_이름을_보여준다`() {
        onView(withId(R.id.recycler_view_theater_selection)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterSelectionViewHolder::class.java),
            ).atPosition(FIRST_ITEM_POSITION),
        )

        onView(withText(makeTheater()[FIRST_MOVIE_ITEM_POSITION].name)).check(matches(isDisplayed()))
    }

    @Test
    fun `극장_목록을_스크롤했을_때_첫_번째_위치의_TheaterSelectionViewHolder는_극장_시간을_보여준다`() {
        onView(withId(R.id.recycler_view_theater_selection)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterSelectionViewHolder::class.java),
            ).atPosition(FIRST_ITEM_POSITION),
        )
        val screeningTimeCount =
            makeTheater()[FIRST_ITEM_POSITION].screeningTimes.weekDay.size + makeTheater()[FIRST_ITEM_POSITION].screeningTimes.weekEnd.size
        println(screeningTimeCount)
        onView(withText(convertScreeningInfoFormat(screeningTimeCount))).check(matches(isDisplayed()))
    }

    companion object {
        private const val FIRST_ITEM_POSITION = 0
    }
}
