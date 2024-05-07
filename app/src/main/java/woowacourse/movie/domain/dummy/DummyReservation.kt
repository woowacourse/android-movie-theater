package woowacourse.movie.domain.dummy

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import java.time.LocalDateTime

object DummyReservation : ReservationRepository {
    private val reservations = mutableListOf<Reservation>()

    override fun saveReservation(
        movieId: Int,
        theaterId: Int,
        title: String,
        ticketCount: Int,
        seats: List<Seat>,
        dateTime: LocalDateTime,
    ): Result<Long> {
        return runCatching {
            val id = reservations.size + 1
            val reservation = Reservation(id.toLong(), theaterId, movieId, title, ticketCount, seats, dateTime)
            reservations.add(reservation)
            id.toLong()
        }
    }

    override fun findReservation(reservationId: Long): Result<Reservation> {
        return runCatching {
            val reservation = reservations.find { reservation -> reservation.reservationId == reservationId }
            reservation ?: throw NoSuchElementException("예약 정보를 찾을 수 없습니다.")
        }
    }

    override fun findReservations(): Result<List<Reservation>> {
        TODO("Not yet implemented")
    }
}
