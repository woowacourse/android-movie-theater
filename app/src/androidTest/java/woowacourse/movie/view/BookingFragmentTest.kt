package woowacourse.movie.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matchers.anything
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.BookingFragment
import woowacourse.movie.R

class BookingFragmentTest {
    private lateinit var scenario: FragmentScenario<BookingFragment>
    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @DisplayName("예약 목록에 예약 아이템 테스트")
    @Test
    fun reservationItem() {
        onView(withId(R.id.lv_reservation))
            .check(matches(isDisplayed()))
    }

    @DisplayName("예약 목록에 영화 타이틀 테스트")
    @Test
    fun movieTitle() {
        onData(anything())
            .inAdapterView(withId(R.id.lv_reservation))
            .atPosition(0)
            .onChildView(withId(R.id.tv_reservation_movie_title))
            .check(matches(isDisplayed()))
    }

    @DisplayName("예약 목록에 영화 예약 날짜")
    @Test
    fun reservationDate() {
        onData(anything())
            .inAdapterView(withId(R.id.lv_reservation))
            .atPosition(0)
            .onChildView(withId(R.id.tv_reservation_date))
            .check(matches(isDisplayed()))
    }

    @DisplayName("예약 목록에 영화 예약 시간")
    @Test
    fun reservationTime() {
        onData(anything())
            .inAdapterView(withId(R.id.lv_reservation))
            .atPosition(0)
            .onChildView(withId(R.id.tv_reservation_time))
            .check(matches(isDisplayed()))
    }


    @DisplayName("예약 목록에 영화 예약 극장")
    @Test
    fun theater() {
        onData(anything())
            .inAdapterView(withId(R.id.lv_reservation))
            .atPosition(0)
            .onChildView(withId(R.id.tv_reservation_theater))
            .check(matches(isDisplayed()))
    }

    @DisplayName("각 요소를 클릭 여부 확인")
    @Test
    fun clickListener() {
        onData(anything())
            .inAdapterView(withId(R.id.lv_reservation))
            .atPosition(0)
            .perform(click())
    }
}
