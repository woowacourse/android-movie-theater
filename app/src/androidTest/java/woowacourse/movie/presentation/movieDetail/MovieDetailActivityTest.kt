package woowacourse.movie.presentation.movieDetail

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.presentation.cinema
import woowacourse.movie.util.testApplicationContext

@RunWith(AndroidJUnit4::class)
class MovieDetailActivityTest {
    private val intent =
        MovieDetailActivity.newIntent(
            testApplicationContext,
            cinema(
                title = "차람과 하디의 진지한 여행기 1",
                year = 2024,
                month = 4,
                day = 25,
                runningTime = 230,
                synopsis = "wow!",
            ),
        )

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule<MovieDetailActivity>(intent)

    @Test
    @DisplayName("예약 화면이 보이는지 테스트")
    fun test() {
        onView(withId(R.id.layout_movie_detail))
            .check(matches(isDisplayed()))
    }

    @Test
    @DisplayName("count 가 1일 때, minus button 클릭 시 count 가 1로 유지 되는지 테스트")
    fun `button_test1`() {
        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")))
        onView(withId(R.id.minus_button)).perform(click())
        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")))
    }

    @Test
    @DisplayName("count 가 1일 때, plus button 클릭 시 count 가 2로 증가하는지 테스트")
    fun `button_test2`() {
        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")))
        onView(withId(R.id.plus_button)).perform(click())
        onView(withId(R.id.quantity_text_view)).check(matches(withText("2")))
    }

    @Test
    @DisplayName(
        "count 가 1일 때, plus button 클릭 시 count 가 2로 증가하고" +
            " minus button 클릭 시 count 가 1로 감소하는 지 테스트",
    )
    fun button_test3() {
        onView(withId(R.id.quantity_text_view))
            .check(matches(withText("1")))
        onView(withId(R.id.plus_button))
            .perform(click())
        onView(withId(R.id.quantity_text_view))
            .check(matches(withText("2")))
        onView(withId(R.id.minus_button))
            .perform(click())
        onView(withId(R.id.quantity_text_view))
            .check(matches(withText("1")))
    }

    @Test
    @DisplayName(
        "date picker 를 통해 Date 를 선택했을 때, 선택한 날짜가 표시되는지 테스트",
    )
    fun date_picker_test() {
        // given
        val expectedDate = "2024.04.01"
        // when: 영화 예약 화면에서 날짜를 선택한다.
        onView(withId(R.id.movie_date_spinner))
            .perform(click())
        onView(withText(expectedDate))
            .perform(click())
        // then: 선택한 날짜가 화면에 표시된다.
        onView(withId(R.id.movie_date_spinner))
            .check(
                matches(withSpinnerText(expectedDate)),
            )
    }

    @Test
    @DisplayName(
        "time picker 를 통해 time 을 선택했을 때, 선택한 Time 이 표시되는지 테스트",
    )
    fun time_picker_test() {
        // given
        val expectedTime = "14:00"
        // when: 영화 예약 화면에서 날짜를 선택한다.
        onView(withId(R.id.movie_time_spinner))
            .perform(click())
        onView(withText(expectedTime))
            .perform(click())
        // then: 선택한 날짜가 화면에 표시된다.
        onView(withId(R.id.movie_time_spinner))
            .check(
                matches(withSpinnerText(expectedTime)),
            )
    }

    @Test
    @DisplayName(
        "좌석 선택 버튼이 활성화 되어 있는지 테스트",
    )
    fun select_button_active_test() {
        onView(withId(R.id.seat_confirmation_button))
            .check(matches(isEnabled()))
    }
}
