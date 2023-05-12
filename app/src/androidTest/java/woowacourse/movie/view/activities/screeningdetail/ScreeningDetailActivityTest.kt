package woowacourse.movie.view.activities.screeningdetail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import woowacourse.movie.R
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.view.activities.screeningdetail.ScreeningDetailActivity.Companion.SCREENING_ID
import java.time.LocalDate
import java.time.LocalTime

class ScreeningDetailActivityTest {

    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        ScreeningDetailActivity::class.java
    ).apply {
        putExtra(SCREENING_ID, 1L)
    }

    @get:Rule
    val rule = ActivityScenarioRule<ScreeningDetailActivity>(intent)

    @Test
    fun 상영_상세_액티비티가_실행되면_좌석_선택이라는_텍스트를_가진_버튼이_표시된다() {
        onView(withText("좌석 선택")).check(matches(isDisplayed()))
    }

    @Test
    fun 예매_인원_수가_1명일_때_마이너스_버튼을_클릭하면_예매_인원_수가_변하지_않는다() {
        onView(withId(R.id.minus_audience_count_btn)).perform(click())

        onView(withId(R.id.audience_count_tv)).check(matches(withText("1")))
    }

    @Test
    fun 예매_인원_수가_20명일_때_플러스_버튼을_클릭하면_예매_인원_수가_변하지_않는다() {
        repeat(19) {
            onView(withId(R.id.plus_audience_count_btn)).perform(click())
        }

        onView(withId(R.id.plus_audience_count_btn)).perform(click())

        onView(withId(R.id.audience_count_tv)).check(matches(withText("20")))
    }

    @Test
    fun 날짜_스피너의_평일인_어떤_날을_선택하면_타임_스피너의_데이터들은_오전_10시부터_자정까지_두_시간_간격의_시간들이다() {
        onView(withId(R.id.date_spinner)).perform(click())
        val anyWeekday = LocalDate.of(2024, 3, 1)
        onData(`is`(anyWeekday))
            .perform(click())

        onView(withId(R.id.time_spinner)).perform(click())
        (10 until 24 step 2).forEach {
            onData(`is`(LocalTime.of(it, 0))).check(matches(isDisplayed()))
        }
    }

    @Test
    fun 날짜_스피너의_주말인_어떤_날을_선택하면_타임_스피너의_데이터들은_오전_9시부터_자정까지_두_시간_간격의_시간들이다() {
        onView(withId(R.id.date_spinner)).perform(click())
        val anyWeekend = LocalDate.of(2024, 3, 2)
        onData(`is`(anyWeekend))
            .perform(click())

        onView(withId(R.id.time_spinner)).perform(click())
        (9 until 24 step 2).forEach {
            onData(`is`(LocalTime.of(it, 0))).check(matches(isDisplayed()))
        }
    }
}
