package woowacourse.movie

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.android.material.switchmaterial.SwitchMaterial
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.activity.MainActivity

class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun clickSetting() {
        onView(withId(R.id.page_setting)).perform(click())
    }

    @Test
    fun `스위치가_켜져있을_때_앱을_재시작하면_스위치가_켜져있다`() {
        setSwitchState(true)
        activityRule.scenario.close()
        ActivityScenario.launch(MainActivity::class.java, null)
        clickSetting()
        onView(withId(R.id.switch_setting_can_push)).check(matches(isChecked()))
    }

    @Test
    fun `스위치가_꺼져있을_떄_앱을_재시작하면_스위치가_꺼져있다`() {
        setSwitchState(false)
        activityRule.scenario.close()
        ActivityScenario.launch(MainActivity::class.java, null)
        clickSetting()
        onView(withId(R.id.switch_setting_can_push)).check(matches(isNotChecked()))
    }

    private fun getSwitchChecked(): Boolean {
        var isChecked = false
        activityRule.scenario.onActivity {
            isChecked = it.findViewById<SwitchMaterial>(R.id.switch_setting_can_push).isChecked
        }
        return isChecked
    }

    private fun setSwitchState(needIsChecked: Boolean) {
        if (getSwitchChecked() != needIsChecked) {
            onView(withId(R.id.switch_setting_can_push)).perform(click())
        }
    }
}
