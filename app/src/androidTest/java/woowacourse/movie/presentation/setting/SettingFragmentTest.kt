package woowacourse.movie.presentation.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.navigation.NavigationActivity
import woowacourse.movie.presentation.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Test
    fun `프래그먼트가_시작하면_알림설정_스위치가_보인다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.selected_fragment,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isDisplayed()))
    }
}
