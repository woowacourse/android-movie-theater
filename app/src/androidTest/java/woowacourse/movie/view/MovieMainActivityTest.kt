package woowacourse.movie.view

import android.content.Context
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.MovieMainActivity
import woowacourse.movie.view.moviemain.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class MovieMainActivityTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Test
    fun 설정_버튼을_누르면_설정_Fragment로_바뀐다() {
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.setting_toggle)).check(matches(isDisplayed()))
    }

    @Test
    fun 설정_Fragment에서_저장된_세팅값이_false면_토글도_꺼져있다() {
        setSharedPreferences(false)
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.setting_toggle)).check(matches(isNotChecked()))
    }

    @Test
    fun 설정_Fragment에서_저장된_세팅값이_true면_토글도_켜져있다() {
        setSharedPreferences(true)
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.setting_toggle)).check(matches(isChecked()))
    }

    private fun setSharedPreferences(isAlarmOn: Boolean) {
        val targetContext: Context = getInstrumentation().targetContext

        val preferencesEditor: SharedPreferences.Editor =
            targetContext.getSharedPreferences(
                SettingFragment.ALARM_SETTING,
                Context.MODE_PRIVATE,
            ).edit()
        preferencesEditor.clear()
        preferencesEditor.putBoolean(SettingFragment.IS_ALARM_ON, isAlarmOn)
        preferencesEditor.commit()
    }
}
