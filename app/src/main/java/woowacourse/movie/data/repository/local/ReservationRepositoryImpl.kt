package woowacourse.movie.data.repository.local

import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.data.model.ReservationEntity
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import java.time.LocalDateTime

class ReservationRepositoryImpl(private val dao: ReservationDao) : ReservationRepository {
    override fun saveReservation(
        movieId: Int,
        theaterId: Int,
        title: String,
        ticketCount: Int,
        seats: List<Seat>,
        dateTime: LocalDateTime,
    ): Result<Long> =
        runCatching {
            val reservationEntity =
                ReservationEntity(theaterId, movieId, title, ticketCount, seats, dateTime)
            dao.saveReservation(reservationEntity)
        }

    override fun findReservation(reservationId: Long): Result<Reservation> =
        runCatching {
            dao.findReservation(reservationId).toDomain()
        }

    override fun findReservations(): Result<List<Reservation>> =
        runCatching {
            dao.findReservations().toDomain()
        }
}
