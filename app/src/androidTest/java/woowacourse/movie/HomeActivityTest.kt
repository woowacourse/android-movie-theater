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

    // Initial displays
    @Test
    fun when_app_starts_movie_lists_showed() {
        onView(withId(R.id.rv_movies))
            .check(matches(isDisplayed()))
    }

    // Initial displays
    @Test
    fun when_app_starts_bottom_navigation_view_showed() {
        onView(withId(R.id.bottom_navigation_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun when_click_reservation_menu_reservation_fragment_showed() {
        onView(withId(R.id.action_reservation_list))
            .perform(click())

        onView(withId(R.id.reservation_fragment_tv))
            .check(matches(isDisplayed()))
    }

    @Test
    fun when_click_settings_menu_settings_fragment_showed() {
        onView(withId(R.id.action_settings))
            .perform(click())

        onView(withId(R.id.settings_fragment_layout))
            .check(matches(isDisplayed()))
    }
}
