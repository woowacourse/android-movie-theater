package woowacourse.movie.presentation.view.screening

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.view.screening.adapter.AdViewHolder
import woowacourse.movie.presentation.view.screening.adapter.MovieViewHolder

@RunWith(AndroidJUnit4::class)
class ScreeningActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(ScreeningActivity::class.java)

    @Test
    fun `영화_목록이_화면에_표시된다`() {
        // given
        onView(withId(R.id.movieList)).check(matches(isDisplayed()))
    }
}
