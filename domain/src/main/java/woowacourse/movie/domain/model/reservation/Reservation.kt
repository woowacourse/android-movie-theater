package woowacourse.movie.domain.model.reservation

import woowacourse.movie.domain.model.movie.MovieDate
import woowacourse.movie.domain.model.movie.MovieTime
import woowacourse.movie.domain.model.movie.TicketPrice
import woowacourse.movie.domain.model.seat.PickedSeats
import woowacourse.movie.domain.model.ticket.Ticket

typealias DomainReservation = Reservation

data class Reservation(
    val movieTitle: String,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticket: Ticket,
    val seats: PickedSeats,
    val ticketPrice: TicketPrice,
)
