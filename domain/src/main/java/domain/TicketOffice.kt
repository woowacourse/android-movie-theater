package domain

import domain.discountPolicy.DisCountPolicies
import domain.seatPolicy.SeatPolicies
import java.time.LocalDateTime

class TicketOffice(
    val tickets: Tickets = Tickets(listOf()),
    val disCountPolicies: DisCountPolicies = DisCountPolicies(),
    val date: LocalDateTime,
    val theaterName: String,
    val peopleCount: Int
) {

    fun generateTicket(seatRow: Int, seatCol: Int): Ticket {
        val seat = Seat(seatRow, seatCol)
        return Ticket(date, seat, theaterName, disCountPolicies)
    }

    fun isAvailableAddTicket(): Boolean = tickets.list.size < peopleCount

    fun addTicket(ticket: Ticket) {
        tickets.addTicket(ticket)
    }

    fun deleteTicket(ticket: Ticket) {
        tickets.deleteTicket(ticket)
    }

    fun updateTickets(ticket: Ticket) {
        if (tickets.isContainSameTicket(ticket)) {
            deleteTicket(ticket)
        } else {
            if (isAvailableAddTicket()) addTicket(ticket)
        }
    }
}
