package woowacourse.movie.ui.setting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R

@RunWith(AndroidJUnit4::class)
class MovieSettingFragmentTest {
    @Before
    fun setUp() {
        val theme = R.style.Theme_Movie
        val fragmentArgs = Bundle()
        launchFragmentInContainer<MovieSettingFragment>(fragmentArgs, theme)
    }

    @Test
    fun 설정_항목들을_보여준다() {
        onView(withId(R.id.switch_setting)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_setting_title)).check(matches(withText("푸시 알림 수신")))
        onView(withId(R.id.tv_setting_description)).check(matches(withText("해제하면 푸시 알림을 수신할 수 없습니다.")))
    }

    @Test
    fun 스위치가_꺼진_상태에서_스위치_클릭_시_켜진_상태로_전환된다() {
        onView(withId(R.id.switch_setting)).perform(click())
        onView(withId(R.id.switch_setting)).check(matches(isChecked()))
    }

    @Test
    fun 스위치가_켜진_상태에서_스위치_클릭_시_꺼진_상태로_전환된다() {
        onView(withId(R.id.switch_setting)).perform(click())
        onView(withId(R.id.switch_setting)).perform(click())
        onView(withId(R.id.switch_setting)).check(matches(isChecked()))
    }
}
