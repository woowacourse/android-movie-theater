package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.main.MainActivity

class SettingFragmentTest {
    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("notification", true).apply()
    }

    @Test
    fun `SharedPreferences의_값에_따라_스위치_상태가_올바르게_설정된다`() {
        onView(withId(R.id.menu_setting))
            .perform(click())

        onView(withId(R.id.switch_alarm))
            .check(matches(isDisplayed()))
            .check(matches(isChecked()))
    }

    @Test
    fun `스위치를_끄면_알람_설정이_false로_저장된다`() {
        onView(withId(R.id.menu_setting))
            .perform(click())

        onView(withId(R.id.switch_alarm)).perform(click())

        val updated = prefs.getBoolean("notification", true)
        assertThat(updated).isFalse()
    }

    @After
    fun finish() {
        context = ApplicationProvider.getApplicationContext()
        prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("notification", false).apply()
    }
}
