package woowacourse.app.data.reservation

import woowacourse.app.data.movie.MovieDataSource
import woowacourse.app.data.movie.MovieMapper.toMovie
import woowacourse.app.data.reservation.ReservationMapper.toReservation
import woowacourse.domain.PaymentType
import woowacourse.domain.movie.Movie
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.ticket.Ticket
import woowacourse.domain.util.CgvError.DataSourceError.DataSourceNoSuchId
import woowacourse.domain.util.CgvResult

class ReservationRepositoryImpl(
    private val reservationDataSource: ReservationDataSource,
    private val seatDataSource: SeatDataSource,
    private val movieDataSource: MovieDataSource,
) :
    ReservationRepository {
    override fun getReservations(): List<Reservation> {
        val reservationEntities = reservationDataSource.getReservationEntities()
        return reservationEntities.map {
            val seatEntities = seatDataSource.getSeatEntities(it.id)
            val movie: Movie = movieDataSource.getMovieEntity(it.movieId)!!.toMovie()
            it.toReservation(seatEntities, movie)
        }
    }

    override fun getReservation(id: Long): CgvResult<Reservation> {
        val reservationEntity = reservationDataSource.getReservationEntity(id)
            ?: return CgvResult.Failure(DataSourceNoSuchId())
        val seatEntities = seatDataSource.getSeatEntities(id)
        val movie: Movie = movieDataSource.getMovieEntity(reservationEntity.movieId)!!.toMovie()
        val reservation = reservationEntity.toReservation(seatEntities, movie)
        return CgvResult.Success(reservation)
    }

    override fun makeReservation(tickets: Set<Ticket>): Reservation {
        if (tickets.isEmpty()) throw IllegalArgumentException()
        val reservationEntity = insertReservationEntity(tickets)
        val seatEntities = insertSeats(tickets, reservationEntity.id)
        val movie: Movie = movieDataSource.getMovieEntity(reservationEntity.movieId)!!.toMovie()
        return reservationEntity.toReservation(seatEntities, movie)
    }

    private fun insertReservationEntity(tickets: Set<Ticket>): ReservationEntity {
        val movieId = tickets.first().movie.id
        val bookedDateTime = tickets.first().bookedDateTime
        val paymentType = PaymentType.OFFLINE.ordinal
        return reservationDataSource.makeReservation(movieId, bookedDateTime, paymentType)
    }

    private fun insertSeats(tickets: Set<Ticket>, reservationId: Long): List<SeatEntity> {
        val seats = tickets.map { it.seat }
        val seatEntities = mutableListOf<SeatEntity>()
        seats.forEach {
            val seatEntity = seatDataSource.insertSeat(
                reservationId = reservationId,
                rank = it.rank.ordinal,
                row = it.position.row,
                column = it.position.column,
            )
            seatEntities.add(seatEntity)
        }
        return seatEntities
    }
}
