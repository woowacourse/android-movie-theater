package woowacourse.movie.presentation.settings

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.R
import woowacourse.movie.presentation.main.MainActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun settingPreference() {
        MovieTheaterApplication.settingsPreference.edit().remove("notification").apply()
    }

    @Test
    fun `기존_설정_값이_true일때_스위치_버튼을_누르면_false가_저장된다`() {
        // given
        val preSet = true
        SharedPreferenceUtil.setNotificationSettings(preSet)
        onView(withId(R.id.action_settings)).perform(click())

        // when
        onView(withId(R.id.switchPushPermission)).perform(click())

        // then
        val expected = false
        val actual = SharedPreferenceUtil.getNotificationSettings()

        assertEquals(expected, actual)
    }

    @Test
    fun `기존_설정_값이_false일때_스위치_버튼을_누르면_true가_저장된다`() {
        // given
        val preSet = false
        SharedPreferenceUtil.setNotificationSettings(preSet)
        onView(withId(R.id.action_settings)).perform(click())

        // when
        onView(withId(R.id.switchPushPermission)).perform(click())

        // then
        val expected = true
        val actual = SharedPreferenceUtil.getNotificationSettings()

        assertEquals(expected, actual)
    }
}
