package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

interface ReservationRepository {
    fun saveReservation(
        movieId: Int,
        theaterId: Int,
        title: String,
        ticketCount: Int,
        seats: List<Seat>,
        dateTime: LocalDateTime,
    ): Result<Long>

    fun findReservation(reservationId: Long): Result<Reservation>

    fun getReservations(): Result<List<Reservation>>

    fun deleteAllReservations(): Result<Unit>
}
