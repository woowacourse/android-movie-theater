package woowacourse.movie.data.mock

import domain.Seat
import domain.Ticket
import domain.Tickets
import domain.discountPolicy.DisCountPolicies
import domain.seatPolicy.SeatPolicies
import java.time.LocalDateTime

object MockTicketsFactory {

    fun makeTicket(row: Int, col: Int): Ticket {
        return Ticket(
            date = LocalDateTime.of(2023, 4, 8, 16, 0),
            seat = Seat(
                row+1, col+1, SeatPolicies()
            ),
            DisCountPolicies()
        )
    }

    fun makeTickets(): Tickets {
        return Tickets(List(3) { makeTicket(it, it) })
    }
}
