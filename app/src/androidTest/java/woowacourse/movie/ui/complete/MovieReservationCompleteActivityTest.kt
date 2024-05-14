package woowacourse.movie.ui.complete

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.FakeUserTicketDao
import woowacourse.movie.R
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketRepositoryImpl
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.model.movie.SeatRow
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class MovieReservationCompleteActivityTest {
    private val userTicket: UserTicket = UserTicketRepositoryImpl.get(FakeUserTicketDao()).find(testUserTicket.id)

    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieReservationCompleteActivity::class.java,
        ).run {
            putExtra(MovieReservationCompleteKey.TICKET_ID, 1L)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieReservationCompleteActivity>(intent)

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        onView(withId(R.id.tv_title))
            .check(matches(isDisplayed()))
            .check(matches(withText(userTicket.movieTitle)))
    }

    @Test
    fun `화면이_띄워지면_상영일시가_보인다`() {
        onView(withId(R.id.tv_screening_date_time))
            .check(matches(isDisplayed()))
            .check(matches(withText("2024.3.28 21:00")))
    }

    @Test
    fun `화면이_띄워지면_인원수_좌석번호_극장명이_보인다`() {
        onView(withId(R.id.tv_selection_result))
            .check(matches(isDisplayed()))
            .check(matches(withText("일반 1명 | A1 | 선릉 극장")))
    }

    @Test
    fun `화면이_띄워지면_예매_금액이_보인다`() {
        val reservationAmount = userTicket.reservationAmount

        onView(withId(R.id.tv_reservation_amount))
            .check(matches(isDisplayed()))
            .check(matches(withText("%,d원 (현장 결제)".format(reservationAmount))))
    }

    companion object {
        private val testUserTicket =
            UserTicket(
                movieTitle = "",
                screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 21, 0),
                reservationCount = 1,
                reservationSeats = listOf(Seat(SeatRow.A, 0)),
                theaterName = "선릉",
                reservationAmount = 10000,
                id = 1L,
            )

        @JvmStatic
        @BeforeClass
        fun setUp() {
            UserTicketRepositoryImpl.get(FakeUserTicketDao()).apply {
                insert(testUserTicket)
            }
        }
    }
}
