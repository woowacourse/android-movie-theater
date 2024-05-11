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
import woowacourse.movie.domain.db.AppDatabase
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ReservationRepositoryImpl
import woowacourse.movie.presentation.ui.util.getDummySeats
import woowacourse.movie.presentation.utils.currency
import woowacourse.movie.presentation.utils.toScreeningDate
import java.time.LocalDateTime
import kotlin.concurrent.thread
import kotlin.properties.Delegates

@RunWith(AndroidJUnit4::class)
class ReservationActivityTest {
    private lateinit var repository: ReservationRepository
    private var reservationId by Delegates.notNull<Long>()

    private val count = 2
    private val reservation =
        Reservation(
            id = 1L,
            theaterName = "선릉",
            movieTitle = "해리 포터",
            ticketCount = count,
            seats = getDummySeats(),
            dateTime = LocalDateTime.of(2024, 5, 10, 11, 16),
        )

    @get:Rule
    val activityRule: ActivityScenarioRule<ReservationActivity> =
        ActivityScenarioRule<ReservationActivity>(
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationActivity::class.java,
            ).apply {
                val context = ApplicationProvider.getApplicationContext<Context>()
                val reservationDao = AppDatabase.getDatabase(context)!!.reservationDao()
                thread {
                    repository = ReservationRepositoryImpl(reservationDao)
                    reservationId = repository.saveReservation(reservation).getOrThrow()
                }
                Thread.sleep(1500)
                putExtra("reservationId", reservationId)
            },
        )

    @Test
    fun `예약한_영화의_제목을_표시한다`() {
        val intent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                ReservationActivity::class.java,
            ).apply {
                putExtra("reservationId", reservationId)
            }
        onView(withId(R.id.tv_reservation_title)).check(matches(withText(reservation.movieTitle)))
    }

    @Test
    fun `예약한_영화의_날짜를_표시한다`() {
        onView(withId(R.id.tv_reservation_date)).check(matches(withText(reservation.dateTime.toScreeningDate())))
    }

    @Test
    fun `상영_영화_예매_수를_표시한다`() {
        onView(
            withId(R.id.tv_reservation_count),
        ).check(matches(withText("일반 ${reservation.ticketCount}명 | ${reservation.seats.toSeatString()} | ${reservation.theaterName}")))
    }

    @Test
    fun `상영_영화_예매_가격을_표시한다`() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        onView(withId(R.id.tv_reservation_amount)).check(
            matches(
                withText(
                    context.getString(
                        R.string.reserve_amount,
                        reservation.totalPrice.currency(context),
                    ),
                ),
            ),
        )
    }
}
