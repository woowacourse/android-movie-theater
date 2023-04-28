package woowacourse.movie.view

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.commit
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.moviemain.MovieMainActivity
import woowacourse.movie.view.moviemain.setting.SettingFragment

@RunWith(AndroidJUnit4::class)
class SettingFragmentTest {
    @get:Rule
    val mActivityTestRule = ActivityScenarioRule(MovieMainActivity::class.java)

    @Before
    fun setup() {
        ActivityScenario.launch(MovieMainActivity::class.java).onActivity {
            it.supportFragmentManager.commit {
                replace(R.id.fragment_container_view, SettingFragment())
            }
        }
    }

    @Test
    fun 설정_Fragment에서_저장된_세팅값이_false면_토글도_꺼져있다() {
        setSharedPreferences(false)
        onView(ViewMatchers.withId(R.id.action_setting)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.setting_toggle))
            .check(ViewAssertions.matches(ViewMatchers.isNotChecked()))
    }

    @Test
    fun 설정_Fragment에서_저장된_세팅값이_true면_토글도_켜져있다() {
        setSharedPreferences(true)
        onView(ViewMatchers.withId(R.id.action_setting)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.setting_toggle))
            .check(ViewAssertions.matches(ViewMatchers.isChecked()))
    }

    private fun setSharedPreferences(isAlarmOn: Boolean) {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

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
