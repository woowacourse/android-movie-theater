package woowacourse.movie.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class MainActivityTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 홈_버튼을_누르면_영화_목록이_표시된다() {
        onView(withId(R.id.home))
            .perform(click())
        onView(withId(R.id.movies))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 예매_내역_버튼을_누르면_예매_내역이_표시된다() {
        onView(withId(R.id.reservation_list))
            .perform(click())
        onView(withId(R.id.reservation_list_fragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 설정_버튼을_누르면_설정_화면이_표시된다() {
        onView(withId(R.id.settings))
            .perform(click())
        onView(withId(R.id.settings_fragment))
            .check(matches(isDisplayed()))
    }
}
