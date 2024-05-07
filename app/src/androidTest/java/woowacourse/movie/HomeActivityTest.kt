package woowacourse.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.presentation.home.HomeActivity

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun `화면의_시작은_영화_목록_페이지이다`() {
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `하단에_네비게이션_바가_보여진다`() {
        onView(withId(R.id.bottom_navigation_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `하단_네비게이션_바에서_예매내역_탭을_누르면_예매내역_화면이_보여진다`() {
        onView(withId(R.id.action_reservation_list))
            .perform(click())

        onView(withId(R.id.rv_reservations))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `하단_네비게이션_바에서_설정_탭을_누르면_설정_화면이_보여진다`() {
        onView(withId(R.id.action_settings))
            .perform(click())

        onView(withId(R.id.settings_fragment_layout))
            .check(matches(isDisplayed()))
    }
}
