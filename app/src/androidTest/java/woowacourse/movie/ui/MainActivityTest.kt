package woowacourse.movie.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R

class MainActivityTest {
    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `네비게이션_바가_존재한다`() {
        onView(withId(R.id.navigation))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `최초_네비게이션은_홈을_가리킨다`() {
        onView(withId(R.id.navigation_home))
            .check(matches(isSelected()))
    }
}
