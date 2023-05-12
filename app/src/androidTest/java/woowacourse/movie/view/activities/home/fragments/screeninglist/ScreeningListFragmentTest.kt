package woowacourse.movie.view.activities.home.fragments.screeninglist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withParentIndex
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.view.activities.home.HomeActivity

class ScreeningListFragmentTest {

    @get:Rule
    val rule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun 상영_목록의_아이템_중_포지션이_3인_아이템은_광고를_보여준다() {
        onView(allOf(withParent(withId(R.id.screening_list_view)), withParentIndex(3)))
            .check(matches(withId(R.id.view_advertisement)))
    }
}
