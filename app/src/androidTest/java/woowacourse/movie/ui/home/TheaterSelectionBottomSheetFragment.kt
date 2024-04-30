package woowacourse.movie.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R

class TheaterSelectionBottomSheetFragment {
    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<MovieHomeActivity> =
        ActivityScenarioRule(MovieHomeActivity::class.java)

    @Test
    fun `각_영화_아이템의_지금_예매_버튼을_클릭하면_극장을_선택할_수_있는_다이얼로그가_나타난다`() {
//        activityScenarioRule.scenario.onActivity {
//
//        }
        onView(withId(R.id.reservation_button)).perform(click())
        onView(withId(R.layout.fragment_theater_selection_bottom_sheet)).check(matches(isDisplayed()))
    }
}
