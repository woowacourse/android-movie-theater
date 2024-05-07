package woowacourse.movie.data.repository

import android.content.Context
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.data.model.ReservationEntity
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.repository.ReservationRepository
import java.time.LocalDateTime

class ReservationRepositoryImpl(context: Context) : ReservationRepository {
    private val dao = ReservationDatabase.getInstance(context)?.dao()

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
            dao?.saveReservation(reservationEntity) ?: throw IllegalArgumentException()
        }

    override fun findReservation(reservationId: Long): Result<Reservation> =
        runCatching {
            dao?.findReservation(reservationId)?.toDomain() ?: throw IllegalArgumentException()
        }

    override fun findReservations(): Result<List<Reservation>> =
        runCatching {
            dao?.findReservations()?.toDomain() ?: throw IllegalArgumentException()
        }
}
