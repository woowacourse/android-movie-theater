package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.checkIsDisplayed
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
    fun 영화_취소_안내_메시지가_보여야_한다() {
        onView(withId(R.id.booked_notice_text))
            .checkIsDisplayed()
    }

    @Test
    fun 영화_제목이_보여야_한다() {
        onView(withId(R.id.booked_movie_title))
            .checkIsDisplayed()
    }

    @Test
    fun 영화_날짜와_시간이_보여야_한다() {
        onView(withId(R.id.booked_date_time))
            .checkIsDisplayed()
    }

    @Test
    fun 영화_인원_수가_보여야_한다() {
        onView(withId(R.id.booked_member_count))
            .checkIsDisplayed()
    }

    @Test
    fun 영화_예매_좌석_정보가_보여야_한다() {
        onView(withId(R.id.booked_booking_seat))
            .checkIsDisplayed()
    }

//    @Test
//    fun 영화_극장_이름이_보여야_한다() {
//        onView(withId(R.id.booked_theater_name))
//            .checkIsDisplayed()
//    }

    @Test
    fun 영화_티켓_가격이_보여야_한다() {
        onView(withId(R.id.booked_ticket_price))
            .checkIsDisplayed()
    }
}
