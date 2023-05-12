package woowacourse.movie.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.storage.SettingsStorage
import woowacourse.movie.view.main.MainActivity

class SettingsFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    private var pushNotificationState = false

    @Before
    fun setUp() {
        pushNotificationState = false

        onView(withId(R.id.bottom_item_settings))
            .perform(click())
    }

    @Test
    fun 푸쉬_알림_설정이_꺼져있을_때_스위치를_누르면_푸쉬_알림_설정이_켜진다() {
        onView(withId(R.id.push_notification_switch))
            .perform(click())

        val mockk = mockk<SettingsStorage>()
        every { mockk.getPushNotification() } returns changeSwitch(pushNotificationState)

        assertThat(mockk.getPushNotification(), `is`(true))
    }

    companion object {
        fun changeSwitch(state: Boolean): Boolean = !state
    }
}
