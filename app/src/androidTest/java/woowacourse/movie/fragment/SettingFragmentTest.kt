package woowacourse.movie.fragment

import android.widget.Switch
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.main.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    private var fragmentScenario: FragmentScenario<SettingFragment>? = null
    private var isAlarmOn: Boolean = false

    @Before
    fun 프레그먼트_띄우기() {
        fragmentScenario = launchFragmentInContainer()
        fragmentScenario?.onFragment {
            isAlarmOn =
                it.requireActivity().findViewById<Switch>(R.id.setting_push_alarm_switch).isChecked
        }
    }

    @Test
    fun 스위치가_화면에_뜨는지_확인한다() {
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        switch.check(matches(isDisplayed()))
    }

    @Test
    fun `꺼져있는_스위치를_클릭하면_켜진다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (isAlarmOn) switch.perform(click())
        // when
        switch.perform(click())
        // then
        switch.check(matches(isChecked()))
    }

    @Test
    fun `켜져있는_스위치를_클릭하면_꺼진다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (!isAlarmOn) switch.perform(click())
        // when
        switch.perform(click())
        // then
        switch.check(matches(isNotChecked()))
    }

    @Test
    fun `스위치를_켜져있는_상태로_앱을_재실행하면_스위치가_켜져있는_상태로_나온다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (!isAlarmOn) switch.perform(click())
        // when
        fragmentScenario?.recreate()
        // then
        switch.check(matches(isChecked()))
    }

    @Test
    fun `스위치를_꺼져있는_상태로_앱을_재실행하면_스위치가_꺼져있는_상태로_나온다`() {
        // given
        val switch = onView(withId(R.id.setting_push_alarm_switch))
        if (isAlarmOn) switch.perform(click())
        // when
        fragmentScenario?.recreate()
        // then
        switch.check(matches(isNotChecked()))
    }
}
