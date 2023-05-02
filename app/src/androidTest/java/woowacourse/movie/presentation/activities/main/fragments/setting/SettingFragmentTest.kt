package woowacourse.movie.presentation.activities.main.fragments.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.MainActivity

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {

    @get:Rule
    internal val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    internal fun 설정_화면_클릭시_화면을_전환한다() {
        onView(withId(R.id.setting))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.notification_push_switch))
            .check(matches(isDisplayed()))
    }

    @Test
    internal fun 푸시_동의_스위치가_활성화_상태일_때_누르면_비활성화한다() {
        onView(withId(R.id.setting))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.notification_push_switch))
            .perform(click())
            .check(matches(isNotChecked()))
    }

    @Test
    internal fun 푸시_동의_스위치가_비활성화_상태일_때_누르면_활성화한다() {
        onView(withId(R.id.setting))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.notification_push_switch))
            .perform(click())
            .check(matches(isChecked()))
    }
}
