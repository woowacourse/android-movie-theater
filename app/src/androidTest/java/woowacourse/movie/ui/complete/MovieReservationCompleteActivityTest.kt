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
import woowacourse.movie.R
import woowacourse.movie.model.data.UserTicketsImpl
import woowacourse.movie.model.movie.ReservationDetail
import woowacourse.movie.model.movie.UserTicket
import woowacourse.movie.ui.selection.MovieSeatSelectionKey
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class MovieReservationCompleteActivityTest {
    private val userTicket: UserTicket = UserTicketsImpl.find(0L)

    private val intent =
        Intent(
            ApplicationProvider.getApplicationContext(),
            MovieReservationCompleteActivity::class.java,
        ).run {
            putExtra(MovieSeatSelectionKey.TICKET_ID, 0L)
        }

    @get:Rule
    val activityRule = ActivityScenarioRule<MovieReservationCompleteActivity>(intent)

    @Test
    fun `화면이_띄워지면_영화_제목이_보인다`() {
        onView(withId(R.id.title_text))
            .check(matches(isDisplayed()))
            .check(matches(withText(userTicket.title)))
    }

    @Test
    fun `화면이_띄워지면_상영일시가_보인다`() {
        onView(withId(R.id.screening_date_time_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("2024.3.28 21:00")))
    }

    @Test
    fun `화면이_띄워지면_인원수_좌석번호_극장명이_보인다`() {
        onView(withId(R.id.selection_result_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("일반 1명 | A1 | 선릉 극장")))
    }

    @Test
    fun `화면이_띄워지면_예매_금액이_보인다`() {
        val reservationAmount = userTicket.reservationDetail.totalSeatAmount()

        onView(withId(R.id.reservation_amount_text))
            .check(matches(isDisplayed()))
            .check(matches(withText("%,d원 (현장 결제)".format(reservationAmount))))
    }

    companion object {
        private const val RESERVATION_COUNT = 1

        @JvmStatic
        @BeforeClass
        fun setUp() {
            val reservationDetail =
                ReservationDetail(RESERVATION_COUNT).apply {
                    addSeat(0, 0) // A1
                }
            UserTicketsImpl.save(
                UserTicket(
                    title = "해리",
                    theater = "선릉",
                    screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 21, 0),
                    reservationDetail = reservationDetail,
                ),
            )
        }
    }
}
