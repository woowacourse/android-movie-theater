package woowacourse.data.reservation

import woowacourse.data.movie.MovieMapper.toMovie
import woowacourse.data.movie.MovieMapper.toMovieEntity
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.SeatRank
import woowacourse.domain.ticket.Ticket

object ReservationMapper {
    fun Reservation.toReservationEntity(): ReservationEntity {
        return ReservationEntity(
            id = ReservationDatabase.getNewId(),
            movie = this.movie.toMovieEntity(),
            bookedDateTime = this.bookedDateTime,
            paymentType = this.paymentType.ordinal,
            seats = this.tickets.map { it.seat.toSeatEntity() },
        )
    }

    fun ReservationEntity.toReservation(): Reservation? {
        return Reservation(
            id = this.id,
            tickets = this.seats.map {
                Ticket(this.movie.toMovie(), this.bookedDateTime, it.toSeat())
            }.toSet(),
            paymentType = woowacourse.domain.PaymentType.find(this.paymentType) ?: return null,
        )
    }

    fun SeatEntity.toSeat(): Seat {
        return Seat(
            SeatRank.find(this.rank),
            Position(this.row, this.column),
        )
    }

    fun Seat.toSeatEntity(): SeatEntity {
        return SeatEntity(
            rank = this.rank.ordinal,
            row = this.position.row,
            column = this.position.column,
        )
    }
}
