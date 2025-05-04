package woowacourse.movie.domain.model.cinema.ticket

import woowacourse.movie.domain.model.cinema.PricePolicy
import woowacourse.movie.domain.model.cinema.screen.Seat
import woowacourse.movie.domain.model.reservation.ReservationInfo

class TicketMachine(
    private val policy: PricePolicy,
) {
    fun publishTickets(
        info: ReservationInfo,
        theaterName: String,
    ): Ticket =
        Ticket(
            info.title,
            theaterName,
            info.reservationDateTime,
            info.seats,
            calculateTotalPrice(info.seats),
        )

    fun calculateTotalPrice(seats: List<Seat>): Int =
        seats.sumOf { seat ->
            policy.calculatePrice(seat.type)
        }

    companion object {
        const val CANCELLATION_TIME = 15
    }
}
