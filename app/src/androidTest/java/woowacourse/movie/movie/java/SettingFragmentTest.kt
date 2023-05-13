package woowacourse.movie.movie.java

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
import woowacourse.movie.main.MainActivity

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun SettingFragment의_switch가_잘_뜨는지_확인() {
        onView(withId(R.id.setting)).perform(click())

        onView(withId(R.id.push_alarm_switch)).check(matches(isDisplayed()))
    }

    @Test
    fun switch를_누르면_switch가_on으로_바뀐다() {
        onView(withId(R.id.setting)).perform(click())

        onView(withId(R.id.push_alarm_switch)).perform(click())

        onView(withId(R.id.push_alarm_switch)).check(matches(isNotChecked()))
    }

    @Test
    fun switch가_on인_상태일때_누르면_switch가_off로_바뀐다() {
        onView(withId(R.id.setting)).perform(click())

        onView(withId(R.id.push_alarm_switch)).perform(click())
        onView(withId(R.id.push_alarm_switch)).perform(click())

        onView(withId(R.id.push_alarm_switch)).check(matches(isNotChecked()))
    }

    @Test
    fun 세팅값이_false이면_스위치가_off_상태인지_확인() {
        // SettingPreference.setBoolean(SETTING_PREFERENCE_KEY, false)

        onView(withId(R.id.setting)).perform(click())
        onView(withId(R.id.push_alarm_switch)).check(matches(isNotChecked()))
    }

    @Test
    fun 세팅값이_true이면_스위치가_on_상태인지_확인() {
        // SettingPreference.setBoolean(SETTING_PREFERENCE_KEY, true)

        onView(withId(R.id.setting)).perform(click())
        onView(withId(R.id.push_alarm_switch)).check(matches(isChecked()))
    }
}
