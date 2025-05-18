package woowacourse.movie.view

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.MovieFixture
import woowacourse.movie.R
import woowacourse.movie.checkIsDisplayed
import woowacourse.movie.checkWithText
import woowacourse.movie.data.Reservation
import woowacourse.movie.data.ReservationRepository
import woowacourse.movie.moviebooked.MovieBookedActivity

class MovieBookedActivityTest {
    private lateinit var scenario: ActivityScenario<MovieBookedActivity>

    @Before
    fun setUp() {
        val reservation = Reservation(
            title = "해리포터와 마법사의 돌",
            date = "2025.04.30" ,
            time = "09:00",
            personnel = 2,
            seats = "B2, B3",
            theater = "선릉",
            price = 20000
        )
        ReservationRepository.initialize(ApplicationProvider.getApplicationContext())
        ReservationRepository.get().insert(reservation)
        val intent = MovieBookedActivity.newIntent(ApplicationProvider.getApplicationContext(), 1L)
        scenario = ActivityScenario.launch(intent)
        scenario.onActivity {
            it.showReservation(reservation)
        }
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
    fun 인텐트로_전달된_영화_제목과_일치한다() {
        onView(withId(R.id.booked_movie_title))
            .checkWithText("해리포터와 마법사의 돌")
    }

    @Test
    fun 영화_날짜가_보여야_한다() {
        onView(withId(R.id.booked_date))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_날짜가_일치한다() {
        onView(withId(R.id.booked_date))
            .checkWithText("2025.04.30")
    }

    @Test
    fun 영화_시간이_보여야_한다() {
        onView(withId(R.id.booked_time))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_시간이_일치한다() {
        onView(withId(R.id.booked_time))
            .checkWithText("09:00")
    }

    @Test
    fun 영화_인원_수가_보여야_한다() {
        onView(withId(R.id.booked_member_count))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_인원_수_일치한다() {
        onView(withId(R.id.booked_member_count))
            .checkWithText(MovieFixture.BOOKING_TICKET_COUNT)
    }

    @Test
    fun 영화_예매_좌석_정보가_보여야_한다() {
        onView(withId(R.id.booked_booking_seat))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_예매_좌석_정보가_일치한다() {
        onView(withId(R.id.booked_booking_seat))
            .checkWithText("B2, B3")
    }

    @Test
    fun 영화_극장_이름이_보여야_한다() {
        onView(withId(R.id.booked_theater_name))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_극장_이름이_일치한다() {
        onView(withId(R.id.booked_theater_name))
            .checkWithText("선릉 극장")
    }

    @Test
    fun 영화_티켓_가격이_보여야_한다() {
        onView(withId(R.id.booked_ticket_price))
            .checkIsDisplayed()
    }

    @Test
    fun 인텐트로_전달된_영화_티켓_가격이_일치한다() {
        onView(withId(R.id.booked_ticket_price))
            .checkWithText("20,000원 (현장 결제)")
    }

    @After
    fun tearDown() {
        scenario.close()
    }
}
