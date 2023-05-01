package woowacourse.movie.presentation.activities.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
internal class MainActivityTest {
    @get:Rule
    internal val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 설정_화면_클릭시_화면을_전환한다() {
        Espresso.onView(ViewMatchers.withId(R.id.setting))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.notification_push_switch))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun 예매목록_화면_클릭시_화면을_전환한다() {
        Espresso.onView(ViewMatchers.withId(R.id.history))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.history_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun 홈_화면_클릭시_화면을_전환한다() {
        Espresso.onView(ViewMatchers.withId(R.id.home))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.movies_rv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
