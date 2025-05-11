package woowacourse.movie.view.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.MainActivity

class SettingFragmentTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun 알림_설정_제목이_표시된다() {
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.tv_notification_setting_title)).check(matches(allOf(
            withText("푸시 알림 수신"),
            isDisplayed(),
        )))
    }

    @Test
    fun 알림_설정_설명이_표시된다() {
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.tv_notification_setting_description)).check(
            matches(
                allOf(
                    withText("해제하면 푸시 알림을 수신할 수 없습니다."),
                    isDisplayed(),
                )
            )
        )
    }

    @Test
    fun 알림_설정_스위치가_표시된다() {
        onView(withId(R.id.action_setting)).perform(click())
        onView(withId(R.id.notification_switch)).check(matches(isDisplayed()))
    }
}
