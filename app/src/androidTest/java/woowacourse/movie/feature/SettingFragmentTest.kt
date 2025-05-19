package woowacourse.movie.feature

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.GrantPermissionRule
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.feature.setting.view.SettingFragment

@Suppress("ktlint:standard:function-naming")
class SettingFragmentTest {
    private lateinit var sharedPreferences: SharedPreferences

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = context.getSharedPreferences("alarmSetting", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
    }

    @Test
    fun 스위치를_켜면_설정값이_true로_저장된다() {
        val scenario = launchFragmentInContainer<SettingFragment>(themeResId = R.style.Theme_Movie)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.notification_switch)).perform(click())

        val value = sharedPreferences.getBoolean("NOTIFICATION_ENABLED", false)
        assertTrue(value)
    }

    @Test
    fun SharedPreferences가_true이면_스위치가_켜진상태로_보인다() {
        sharedPreferences.edit().putBoolean("NOTIFICATION_ENABLED", true).apply()

        val scenario = launchFragmentInContainer<SettingFragment>(themeResId = R.style.Theme_Movie)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.notification_switch)).check { view, _ ->
            assertTrue((view as androidx.appcompat.widget.SwitchCompat).isChecked)
        }
    }

    @Test
    fun 스위치를_끄면_설정값이_false로_저장된다() {
        sharedPreferences.edit().putBoolean("NOTIFICATION_ENABLED", true).commit()

        val scenario = launchFragmentInContainer<SettingFragment>(themeResId = R.style.Theme_Movie)
        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.notification_switch)).perform(click())

        val value = sharedPreferences.getBoolean("NOTIFICATION_ENABLED", false)
        assertFalse(value)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear().commit()
    }
}
