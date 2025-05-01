package woowacourse.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.view.movie.MoviesActivity

class MoviesActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MoviesActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @Test
    fun `예매_목록_프래그먼트를_선택하면_예매_목록_화면이_보여야_한다`() {
        onView(withId(R.id.fragment_list)).perform(click())

        onView(withText("예매내역 화면입니다."))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 설정_프래그먼트를_선택하면_설정_화면이_보여야_한다() {
        onView(withId(R.id.fragment_setting)).perform(click())

        onView(withText("설정화면 입니다"))
            .check(matches(isDisplayed()))
    }

    @After
    fun finish() {
        Intents.release()
    }
}
