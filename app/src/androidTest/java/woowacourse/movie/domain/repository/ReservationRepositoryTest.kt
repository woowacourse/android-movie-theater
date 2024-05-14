package woowacourse.movie.domain.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.repository.local.ReservationRepositoryImpl
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.ui.util.getDummyMovie
import woowacourse.movie.presentation.ui.util.getDummyReservation
import woowacourse.movie.presentation.ui.util.getDummySeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationRepositoryTest {
    private lateinit var repository: ReservationRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = ReservationDatabase.getDatabase(context)
        repository = ReservationRepositoryImpl(database.dao())
    }

    @After
    fun tearDown() {
        repository.deleteAllReservations()
    }

    @Test
    fun `예약_내역을_저장하면_예약_id_값을_반환한다`() {
        val reservation = getDummyReservation()

        val reservationId =
            repository.saveReservation(
                reservation.movieId,
                reservation.theaterId,
                reservation.title,
                reservation.ticketCount,
                reservation.seats,
                reservation.dateTime,
            ).getOrThrow()

        assertThat(reservationId).isEqualTo(1L)
    }

    @Test
    fun `예약_내역을_저장하고_저장된_예약_id를_통해_예약_내역을_확인할_수_있다`() {
        val reservation =
            Reservation(
                reservationId = 1,
                theaterId = 1,
                movieId = getDummyMovie().id,
                title = getDummyMovie().title,
                ticketCount = 3,
                seats = getDummySeats(),
                dateTime = LocalDateTime.of(LocalDate.of(2024, 5, 13), LocalTime.of(0, 0)),
            )

        val reservationId =
            repository.saveReservation(
                reservation.movieId,
                reservation.theaterId,
                reservation.title,
                reservation.ticketCount,
                reservation.seats,
                reservation.dateTime,
            ).getOrThrow()

        val actual = repository.findReservation(reservationId).getOrThrow()

        assertThat(actual).isEqualTo(
            Reservation(
                reservationId = reservationId,
                theaterId = 1,
                movieId = getDummyMovie().id,
                title = getDummyMovie().title,
                ticketCount = 3,
                seats = getDummySeats(),
                dateTime = LocalDateTime.of(LocalDate.of(2024, 5, 13), LocalTime.of(0, 0)),
            ),
        )
    }

    @Test
    fun `예약_내역을_저장하고_모든_예약_내역을_확인할_수_있다`() {
        val reservation = getDummyReservation()

        repository.saveReservation(
            reservation.movieId,
            reservation.theaterId,
            reservation.title,
            reservation.ticketCount,
            reservation.seats,
            reservation.dateTime,
        ).getOrThrow()

        val actual = repository.getReservations().getOrThrow()

        assertThat(actual.size).isEqualTo(1)
    }
}
