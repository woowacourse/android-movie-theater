package woowacourse.movie.domain.model.reservation

import woowacourse.movie.domain.model.movie.MovieDate
import woowacourse.movie.domain.model.movie.MovieTime
import woowacourse.movie.domain.model.movie.TicketPrice
import woowacourse.movie.domain.model.seat.PickedSeats
import woowacourse.movie.domain.model.ticket.Ticket

typealias DomainReservation = Reservation

class Reservation private constructor(
    val movieTitle: String,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticket: Ticket,
    val seats: PickedSeats,
    val ticketPrice: TicketPrice,
) {
    companion object {
        fun of(
            movieTitle: String,
            year: Int,
            month: Int,
            day: Int,
            hour: Int,
            min: Int,
            ticketCount: Int,
            seats: PickedSeats,
            price: TicketPrice,
        ): Reservation = Reservation(
            movieTitle,
            MovieDate(year, month, day),
            MovieTime(hour, min),
            Ticket(ticketCount),
            seats,
            price
        )
    }
}
