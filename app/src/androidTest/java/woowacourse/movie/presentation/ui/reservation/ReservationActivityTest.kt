package woowacourse.movie.presentation.ui.reservation

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.repository.local.ReservationRepositoryImpl
import woowacourse.movie.data.repository.remote.DummyTheater
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.presentation.ui.util.currency
import woowacourse.movie.presentation.ui.util.getDummyReservation
import woowacourse.movie.presentation.utils.toScreeningDate
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class ReservationActivityTest {
    private var reservationRepository: ReservationRepository
    private val theaterRepository: TheaterRepository by lazy { DummyTheater }
    private lateinit var reservation: Reservation
    private var reservationId: Long? = null

    @get:Rule
    val activityRule: ActivityScenarioRule<ReservationActivity> =
        ActivityScenarioRule<ReservationActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationActivity::class.java,
            ).apply {
                val context = ApplicationProvider.getApplicationContext<Context>()
                reservationRepository = ReservationRepositoryImpl(context)
                thread {
                    reservationId =
                        reservationRepository.saveReservation(
                            movieId = getDummyReservation().movieId,
                            theaterId = getDummyReservation().theaterId,
                            title = getDummyReservation().title,
                            ticketCount = getDummyReservation().ticketCount,
                            seats = getDummyReservation().seats,
                            dateTime = getDummyReservation().dateTime,
                        ).getOrThrow()
                }
                Thread.sleep(1000)

                reservationId?.let { id ->
                    reservation = reservationRepository.findReservation(id).getOrThrow()
                }

                putExtra("reservationId", reservationId)
            },
        )

    @Test
    fun `예약한_영화의_제목을_표시한다`() {
        onView(withId(R.id.tv_reservation_title)).check(matches(withText(reservation.title)))
    }

    @Test
    fun `예약한_영화의_날짜를_표시한다`() {
        onView(withId(R.id.tv_reservation_date)).check(matches(withText(reservation.dateTime.toScreeningDate())))
    }

    @Test
    fun `상영_영화_예매_수를_표시한다`() {
        val theaterName = theaterRepository.findTheaterNameById(reservation.theaterId).getOrThrow()

        onView(
            withId(R.id.tv_reservation_count),
        ).check(matches(withText("일반 ${reservation.ticketCount}명 | ${reservation.seats.toSeatString()} | $theaterName")))
    }

    @Test
    fun `상영_영화_예매_가격을_표시한다`() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        onView(withId(R.id.tv_reservation_amount)).check(
            matches(withText(reservation.currency(context))),
        )
    }
}
