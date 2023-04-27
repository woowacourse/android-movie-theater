package woowacourse.movie.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.ui.activity.MainActivity
import woowacourse.movie.ui.storage.SettingsStorage

class SettingsFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        SettingsStorage.editPushNotification(false)

        onView(withId(R.id.bottom_item_settings))
            .perform(click())
    }

    @Test
    fun 푸쉬_알림_설정이_꺼져있을_때_스위치를_누르면_푸쉬_알림_설정이_켜진다() {
        onView(withId(R.id.push_notification_switch))
            .perform(click())

        assert(SettingsStorage.getPushNotification())
    }
}
