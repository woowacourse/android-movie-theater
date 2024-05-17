package woowacourse.movie.presentation.navigation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.navigation.NavigationActivity

@RunWith(AndroidJUnit4::class)
class NavigationActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Test
    fun `액티비티가_시작하면_navigationBar가_보인다`() {
        onView(withId(R.id.navigationView)).check(matches(isDisplayed()))
    }
}
