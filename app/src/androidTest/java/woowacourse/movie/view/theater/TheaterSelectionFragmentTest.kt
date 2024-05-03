package woowacourse.movie.view.theater

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.TestFixture.FIRST_MOVIE_ITEM_POSITION
import woowacourse.movie.TestFixture.makeTheater
import woowacourse.movie.utils.MovieUtils.convertScreeningInfoFormat
import woowacourse.movie.utils.MovieUtils.navigateToBottomMenu
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.home.HomeFragment
import woowacourse.movie.view.theater.adapter.TheaterSelectionViewHolder

@RunWith(AndroidJUnit4::class)
@MediumTest
class TheaterSelectionFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                HomeFragment(),
            )
            val bundle = Bundle()
            bundle.putInt(HomeFragment.MOVIE_ID, 0)
            val bottomSheetDialogFragment = TheaterSelectionFragment()
            bottomSheetDialogFragment.arguments = bundle
            bottomSheetDialogFragment.show(it.supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    @Test
    fun `극장_목록을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler_view_theater_selection)).check(matches(isDisplayed()))
    }

    @Test
    fun `극장_목록을_스크롤했을_때_첫_번째_위치의_TheaterSelectionViewHolder는_극장_이름을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler_view_theater_selection)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterSelectionViewHolder::class.java),
            ).atPosition(FIRST_ITEM_POSITION),
        )

        onView(withText(makeTheater()[FIRST_MOVIE_ITEM_POSITION].theaterName)).check(matches(isDisplayed()))
    }

    @Test
    fun `극장_목록을_스크롤했을_때_첫_번째_위치의_TheaterSelectionViewHolder는_극장_시간을_보여준다`() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler_view_theater_selection)).perform(
            RecyclerViewActions.scrollToHolder(
                instanceOf(TheaterSelectionViewHolder::class.java),
            ).atPosition(FIRST_ITEM_POSITION),
        )
        val screeningTimeCount =
            makeTheater()[FIRST_ITEM_POSITION].screeningTimes.weekDay.size + makeTheater()[FIRST_ITEM_POSITION].screeningTimes.weekEnd.size
        val text = "${screeningTimeCount}개의 상영 시간"
        println(text)
        onView(withText(text)).check(matches(isDisplayed()))
    }

    companion object {
        const val FIRST_ITEM_POSITION = 0
    }
}
