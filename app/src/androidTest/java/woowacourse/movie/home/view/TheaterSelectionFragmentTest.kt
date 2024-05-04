package woowacourse.movie.home.view

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
import woowacourse.movie.home.view.adapter.theater.TheaterViewHolder
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID

@RunWith(AndroidJUnit4::class)
class TheaterSelectionFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                MovieHomeFragment(),
            ).commit()
            val bundle = Bundle()
            bundle.putLong(KEY_MOVIE_ID, 0L)
            val theaterSelectionFragment = TheaterSelectionFragment()
            theaterSelectionFragment.arguments = bundle
            theaterSelectionFragment.show(it.supportFragmentManager, theaterSelectionFragment.tag)
        }
    }

    @Test
    fun `극장_목록을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler_view_theater)).check(matches(isDisplayed()))
    }

    @Test
    fun `두_번째의_극장_이름을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler_view_theater)).perform(
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
        onView(withId(R.id.recycler_view_theater)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterViewHolder::class.java),
            ).atPosition(1),
        )

        onView(withText("5"))
            .check(matches(isDisplayed()))
    }
}
