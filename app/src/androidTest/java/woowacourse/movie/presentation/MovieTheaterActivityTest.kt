package woowacourse.movie.presentation

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.presentation.history.ReservationHistoryFragment
import woowacourse.movie.presentation.home.movies.MoviesFragment
import woowacourse.movie.presentation.setting.SettingFragment

class MovieTheaterActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MovieTheaterActivity::class.java)

    @Test
    fun `홈_탭을_클릭하면_영화_목록_화면이_표시된다`() {
        onView(withId(R.id.menu_home))
            .perform(click())
            .check(matches(isSelected()))

        assertThat(isFragmentVisible(MoviesFragment::class.java)).isTrue()
    }

    @Test
    fun `예약_내역_탭을_클릭하면_예약_내역_화면이_표시된다`() {
        onView(withId(R.id.menu_history))
            .perform(click())
            .check(matches(isSelected()))

        assertThat(isFragmentVisible(ReservationHistoryFragment::class.java)).isTrue()
    }

    @Test
    fun `설정_탭을_클릭하면_설정_화면이_표시된다`() {
        onView(withId(R.id.menu_setting))
            .perform(click())
            .check(matches(isSelected()))

        assertThat(isFragmentVisible(SettingFragment::class.java)).isTrue()
    }

    private fun isFragmentVisible(expectedClass: Class<out Fragment>): Boolean {
        var fragment: Fragment? = null

        activityScenarioRule.scenario.onActivity { activity ->
            fragment = activity.supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        }

        return expectedClass.isInstance(fragment)
    }
}
