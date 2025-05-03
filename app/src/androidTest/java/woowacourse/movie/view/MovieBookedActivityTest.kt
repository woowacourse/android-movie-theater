package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.domain.seat.Column
import woowacourse.movie.domain.seat.Row
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.domain.seat.SeatGrade
import woowacourse.movie.moviebooked.MovieBookedActivity

class MovieBookedActivityTest {
    @Before
    fun setUp() {
        val bookingStatus = MovieFixture.BOOKING_STATUS
        val theater = MovieFixture.THEATER

        bookingStatus.seat.add(Seat(Row(1), Column(1), SeatGrade.B))
        bookingStatus.seat.add(Seat(Row(1), Column(2), SeatGrade.B))

        val intent = MovieBookedActivity.movieBookedIntent(ApplicationProvider.getApplicationContext(), bookingStatus, theater)

        ActivityScenario.launch<MovieBookedActivity>(intent)
    }

    @Test
    @DisplayName("영화 취소 안내 메시지가 보여야 한다")
    fun ticketCancelInfoIsDisplayed() {
        onView(withId(R.id.notice_text))
            .check(matches(withText("영화 상영 시작 시간 15분 전까지\n취소가 가능합니다.")))
    }
}
