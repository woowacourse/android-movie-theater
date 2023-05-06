package woowacourse.movie.data.mock

import domain.Seat
import domain.Ticket
import domain.Tickets
import domain.discountPolicy.DisCountPolicies
import domain.seatPolicy.SeatPolicies
import java.time.LocalDateTime

object MockTicketsFactory {

    fun makeTicket(): Ticket {
        return Ticket(
            date = LocalDateTime.of(2023, 4, 8, 16, 0),
            seat = Seat(
                1, 1, SeatPolicies()
            ),
            DisCountPolicies()
        )
    }

    fun makeTickets(): Tickets {
        return Tickets(List(5) { makeTicket() })
    }
}
