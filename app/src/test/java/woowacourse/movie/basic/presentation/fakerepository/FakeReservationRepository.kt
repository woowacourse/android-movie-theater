package woowacourse.movie.basic.presentation.fakerepository

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import java.time.LocalDateTime

class FakeReservationRepository(
    private val id: Int,
    private val reservation: Reservation,
) : ReservationRepository {
    override fun saveReservation(
        movie: ScreenView.Movie,
        ticketCount: Int,
        seats: List<Seat>,
        dateTime: LocalDateTime,
    ): Result<Int> {
        return runCatching { id }
    }

    override fun findByReservationId(id: Int): Result<Reservation> {
        return runCatching {
            if (id != this.id) throw NoSuchElementException()
            reservation
        }
    }
}
