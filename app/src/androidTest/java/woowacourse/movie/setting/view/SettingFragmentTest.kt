package woowacourse.movie.setting.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieMainActivity
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Test
    fun `알림_설정을_하는_switch가_보인다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `알림_권한이_부여되지_않으면_스위치가_비활성화_되고_스위치가_OFF상태가_된다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isNotEnabled()))

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isNotChecked()))
    }

    @Test
    fun `알림_권한이_부여되면_스위치가_활성화_되고_스위치가_ON상태가_된다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isEnabled()))

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isChecked()))
    }

    @Test
    fun `알림_권한이_부여되어_있을떄_스위치를_한번_클릭하면_스위치가_OFF_상태가_된다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .perform(click())

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isNotChecked()))
    }

    @Test
    fun `알림_권한이_부여되어_있을떄_스위치를_두번_클릭하면_스위치가_ON_상태가_된다`() {
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragment_main_container,
                SettingFragment(),
            ).commit()
        }

        onView(withId(R.id.switch_setting_alarm))
            .perform(click())
            .perform(click())

        onView(withId(R.id.switch_setting_alarm))
            .check(matches(isChecked()))
    }
}
