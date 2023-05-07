package woowacourse.app.data.reservation

import woowacourse.domain.PaymentType
import woowacourse.domain.movie.Movie
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.SeatRank
import woowacourse.domain.ticket.Ticket

object ReservationMapper {

    fun ReservationEntity.toReservation(seats: List<SeatEntity>, movie: Movie): Reservation {
        return Reservation(
            id = this.id,
            tickets = seats.map {
                Ticket(movie, this.bookedDateTime, it.toSeat())
            }.toSet(),
            paymentType = PaymentType.find(this.paymentType) ?: PaymentType.OFFLINE,
        )
    }

    fun SeatEntity.toSeat(): Seat {
        return Seat(
            SeatRank.find(this.rank),
            Position(this.row, this.column),
        )
    }
}
