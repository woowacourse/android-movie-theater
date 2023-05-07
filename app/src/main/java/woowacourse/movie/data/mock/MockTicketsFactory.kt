package woowacourse.movie.data.mock

import domain.Seat
import domain.Theater
import domain.Ticket
import domain.Tickets
import domain.discountPolicy.DisCountPolicies
import domain.seatPolicy.SeatPolicies
import java.time.LocalDateTime
import java.time.LocalTime

object MockTicketsFactory {

    fun makeTicket(row: Int, col: Int): Ticket {
        return Ticket(
            date = LocalDateTime.of(2023, 4, 8, 16, 0),
            seat = Seat(
                row + 1, col + 1, SeatPolicies()
            ),
            DisCountPolicies(),
            Theater(
                "잠실 극장",
                listOf(
                    LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)
                )
            )
        )
    }

    fun makeTickets(): Tickets {
        return Tickets(List(3) { makeTicket(it, it) })
    }
}
