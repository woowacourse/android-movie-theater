package woowacourse.movie.view.history

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.MainActivity

@Suppress("FunctionName")
class BookingHistoryFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 예매_내역이_표시된다() {
        onView(withId(R.id.action_history)).perform(click())
        onView(withId(R.id.rv_booking_history)).check(matches(isDisplayed()))
    }
}
