package woowacourse.movie.ui.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.model.AlarmSwitchState
import woowacourse.movie.ui.MainActivity

@RunWith(AndroidJUnit4::class)
internal class SettingFragmentTest {
    @get:Rule
    internal val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun resetSharedPreference() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        AlarmSwitchState.init(context)
        AlarmSwitchState.isAlarmActivated = false

        onView(withId(R.id.menu_item_setting))
            .perform(click())
    }

    @Test
    fun 스위치가_선택된다() {
        onView(withId(R.id.setting_switch))
            .check(matches(isNotChecked()))
            .perform(click())
            .check(matches(isChecked()))

        assertEquals(true, AlarmSwitchState.isAlarmActivated)
    }

    @Test
    fun 스위치의_선택_상태가_유지된다() {
        onView(withId(R.id.setting_switch))
            .perform(click())
            .check(matches(isChecked()))

        onView(withId(R.id.menu_item_home))
            .perform(click())
        onView(withId(R.id.menu_item_setting))
            .perform(click())

        onView(withId(R.id.setting_switch))
            .check(matches(isChecked()))
    }
}
