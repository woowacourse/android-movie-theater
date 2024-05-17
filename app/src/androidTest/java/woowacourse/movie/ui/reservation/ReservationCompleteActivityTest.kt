package woowacourse.movie.ui.reservation

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.repository.DummyReservation
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationCompleteActivityTest {
    private lateinit var dummyReservation: Reservation

    @get:Rule
    val activityRule: ActivityScenarioRule<ReservationCompleteActivity> =
        ActivityScenarioRule<ReservationCompleteActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationCompleteActivity::class.java,
            ).apply {
                putExtra("reservationId", testFixtureReservationId())
                putExtra("theaterId", 1)
            },
        )

    @Before
    fun setup() {
        dummyReservation = DummyReservation.findById(1).getOrThrow()
    }

    @Test
    fun `예약한_영화의_제목을_표시한다`() {
        val movieTitle = dummyReservation.screen.movie.title

        onView(withId(R.id.tv_reservation_title)).check(matches(withText(movieTitle)))
    }

    @Test
    fun `예약_날짜를_보여준다`() {
        val date = dummyReservation.dateTime?.date

        onView(withId(R.id.tv_reservation_date)).check(matches(withText(date.toString())))
    }

    @Test
    fun `예약_시각을_보여준다`() {
        val time = dummyReservation.dateTime?.time

        onView(withId(R.id.tv_reservation_time)).check(matches(withText(time.toString())))
    }

    @Test
    fun `상영_영화_예매_수를_표시한다`() {
        val ticketCount = dummyReservation.ticket.count

        onView(withId(R.id.tv_reservation_count)).check(
            matches(
                withText(
                    ("일반 %d명 |").format(
                        ticketCount,
                    ),
                ),
            ),
        )
    }

    @Test
    fun `상영_영화_예매_가격을_표시한다`() {
        val totalPrice = dummyReservation.seats.totalPrice()

        onView(withId(R.id.tv_reservation_amount)).check(
            matches(
                withText(
                    ("%s(현장 결제)").format(
                        totalPrice,
                    ),
                ),
            ),
        )
    }

    @Test
    fun `예약_좌석을_보여준다`() {
        val seats = dummyReservation.seats.seats

        onView(withId(R.id.tv_reservation_seats)).check(
            matches(
                withText(
                    seats.joinToString(
                        separator = ",",
                        postfix = " |",
                    ) { "${'A' + it.position.row}${it.position.col + 1}" },
                ),
            ),
        )
    }

    companion object {
        val screen = Screen.NULL
        val seats =
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(1, 1), Grade.A),
            )
        val dateTime = DateTime(LocalDate.of(2024, 3, 1), LocalTime.of(10, 0))
    }

    private fun testFixtureReservationId(): Int {
        return DummyReservation.save(
            screen,
            seats,
            dateTime,
        ).getOrThrow()
    }
}
