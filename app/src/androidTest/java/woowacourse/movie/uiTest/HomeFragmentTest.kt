package woowacourse.movie.uiTest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.view.home.HomeFragment

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<HomeFragment>()
    }

    @Test
    fun `영화_목록_리사이클러뷰가_화면에_표시된다`() {
        onView(withId(R.id.recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화목록_첫번째아이템_영화제목이_표시된다`() {
        onView(withText("해리포터"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `영화목록_스크롤하여_영화제목이_표시된다`() {
        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))

        onView(withText("어거스트 러쉬"))
            .check(matches(isDisplayed()))
    }
}
