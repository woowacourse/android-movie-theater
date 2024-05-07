package woowacourse.movie.view.history

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.utils.MovieUtils.navigateToBottomMenu
import woowacourse.movie.view.MainActivity

@RunWith(AndroidJUnit4::class)
class ReservationHistoryFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.navigateToBottomMenu(
                R.id.fragment_container_main,
                ReservationHistoryFragment(),
            )
        }
    }

    @Test
    fun `예약_목록을_보여준다`() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_history))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
