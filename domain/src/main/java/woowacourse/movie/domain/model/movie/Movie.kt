package woowacourse.movie.domain.model.movie

import woowacourse.movie.domain.model.reservation.Reservation
import woowacourse.movie.domain.model.seat.PickedSeats
import woowacourse.movie.domain.model.ticket.Ticket
import java.time.LocalDate

typealias DomainMovie = Movie

data class Movie(
    val id: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val introduce: String,
    val thumbnail: Int,
) {
    fun reserve(
        theaterName: String,
        reservedDate: MovieDate,
        reservedTime: MovieTime,
        ticket: Ticket,
        seats: PickedSeats,
        ticketPrice: TicketPrice
    ): Reservation {
        checkReservedDate(reservedDate)

        return Reservation(
            title,
            theaterName,
            reservedDate,
            reservedTime,
            ticket,
            seats,
            ticketPrice
        )
    }

    private fun checkReservedDate(reservedDate: MovieDate) {
        reservedDate.checkInRange(startDate, endDate)
    }
}
