package woowacourse.movie

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.activity.MainActivity

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var isPossiblePushAlarm: Boolean = false

    @Before
    fun open_setting_fragment() {
        val navigationBarSetting = onView(withId(R.id.setting_bottom_navigation))
        navigationBarSetting.perform(click())

        val context = ApplicationProvider.getApplicationContext<Application>()
        val sharedPreferences = context
            .getSharedPreferences("settings", MODE_PRIVATE)
        isPossiblePushAlarm = sharedPreferences.getBoolean("pushAlarm", false)
    }

    @Test
    fun check_switch_displayed() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        // then
        switch.check(matches(isDisplayed()))
    }

    @Test
    fun `꺼져있는_스위치를_클릭하면_켜진다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (isPossiblePushAlarm) switch.perform(click())
        // when
        switch.perform(click())
        // then
        switch.check(matches(isChecked()))
    }

    @Test
    fun `켜져있는_스위치를_클릭하면_꺼진다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (!isPossiblePushAlarm) switch.perform(click())
        // when
        switch.perform(click())
        // then
        switch.check(matches(isNotChecked()))
    }

    @Test
    fun `스위치를_켜져있는_상태로_앱을_재실행하면_스위치가_켜져있는_상태로_나온다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (!isPossiblePushAlarm) switch.perform(click())
        // when
        activityRule.scenario.recreate()
        // then
        switch.check(matches(isChecked()))
    }

    @Test
    fun `스위치를_꺼져있는_상태로_앱을_재실행하면_스위치가_꺼져있는_상태로_나온다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (isPossiblePushAlarm) switch.perform(click())
        // when
        activityRule.scenario.recreate()
        // then
        switch.check(matches(isNotChecked()))
    }
}
