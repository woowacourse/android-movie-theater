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
import woowacourse.movie.presentation.ui.util.getDummyReservation

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

        val actual = repository.findReservation(reservationId).getOrThrow()

        assertThat(actual).isEqualTo(reservation.copy(reservationId = actual.reservationId))
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
