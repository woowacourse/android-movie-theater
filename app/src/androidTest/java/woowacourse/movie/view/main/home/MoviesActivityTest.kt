package woowacourse.movie.view.main.home

import android.Manifest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.main.MoviesActivity

class MoviesActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MoviesActivity::class.java)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.POST_NOTIFICATIONS,
        )

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun `예매_목록_프래그먼트를_선택하면_예매_목록_화면이_보여야_한다`() {
        onView(withId(R.id.fragment_list)).perform(click())

        onView(withId(R.id.reservation_list_root_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `설정_프래그먼트를_선택하면_설정_화면이_보여야_한다`() {
        onView(withId(R.id.fragment_setting)).perform(click())

        onView(withId(R.id.setting_root_layout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `설정_화면에서_푸시_알림_수신_스위치가_화면에_보여야_한다`() {
        onView(withId(R.id.fragment_setting)).perform(click())

        onView(withId(R.id.setting_notification_switch))
            .check(matches(isDisplayed()))
    }

    @After
    fun finish() {
        Intents.release()
    }
}
