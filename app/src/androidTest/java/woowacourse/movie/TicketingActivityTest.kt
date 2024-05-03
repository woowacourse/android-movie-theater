package woowacourse.movie

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.presentation.ticketing.TicketingActivity

@RunWith(AndroidJUnit4::class)
class TicketingActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<TicketingActivity> =
        ActivityScenarioRule<TicketingActivity>(
            Intent(ApplicationProvider.getApplicationContext(), TicketingActivity::class.java).apply {
                putExtra("movie_id", 1L)
                putExtra("theater_id", 1L)
            },
        )

    private fun performClick(viewId: Int) {
        onView(withId(viewId)).perform(click())
    }

    @Test
    fun initial_count_is_one() {
        onView(withId(R.id.tv_count))
            .check(matches(withText("1")))
    }

    @Test
    fun `증가_버튼을_누르면_숫자가_증가한다`() {
        performClick(R.id.btn_plus)
        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun `감소_버튼을_누르면_숫자가_감소한다`() {
        performClick(R.id.btn_plus)
        performClick(R.id.btn_minus)
        onView(withId(R.id.tv_count)).check(matches(withText("1")))
    }

    @Test
    fun `숫자가_예매_가능한_최솟값일_경우_감소_버튼을_누르면_숫자가_감소하지_않는다`() {
        performClick(R.id.btn_minus)
        onView(withId(R.id.tv_count)).check(matches(withText("1")))
    }

    @Test
    fun `인원_수_증가_후_화면_회전_시에도_인원수가_유지된다`() {
        // given
        val activityScenario = activityRule.scenario
        performClick(R.id.btn_plus)

        // when
        activityScenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        // then
        onView(withId(R.id.tv_count)).check(matches(withText("2")))
    }

    @Test
    fun spinners_exist_on_display() {
        onView(withId(R.id.spinner_date))
            .check(matches(isDisplayed()))

        onView(withId(R.id.spinner_time_slot))
            .check(matches(isDisplayed()))
    }
}
