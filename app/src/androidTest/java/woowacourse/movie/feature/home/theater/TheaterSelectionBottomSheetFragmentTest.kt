package woowacourse.movie.feature.home.theater

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieMainActivity
import woowacourse.movie.R
import woowacourse.movie.feature.firstMovieId
import woowacourse.movie.feature.home.movie.MovieHomeFragment
import woowacourse.movie.feature.home.theater.adapter.TheaterViewHolder
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

@RunWith(AndroidJUnit4::class)
class TheaterSelectionBottomSheetFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container_view,
                MovieHomeFragment(),
            ).commit()
            val bundle = Bundle()
            bundle.putLong(KEY_MOVIE_ID, firstMovieId)
            val theaterSelectionBottomSheetFragment = TheaterSelectionBottomSheetFragment()
            theaterSelectionBottomSheetFragment.arguments = bundle
            theaterSelectionBottomSheetFragment.show(it.supportFragmentManager, theaterSelectionBottomSheetFragment.tag)
        }
    }

    @Test
    fun `극장_목록을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.theater_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun `두_번째의_극장_이름을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.theater_recyclerview)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterViewHolder::class.java),
            ).atPosition(1),
        )

        onView(withText("잠실 극장"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `두_번째의_극장의_상영_시간_개수를_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.theater_recyclerview)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterViewHolder::class.java),
            ).atPosition(1),
        )

        onView(withText("5"))
            .check(matches(isDisplayed()))
    }
}
