package woowacourse

import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.repository.ReservationTicketRepository

class MockReservationTicketRepository : ReservationTicketRepository {
    override fun loadReservationTickets(): List<ReservationTicket> {
        TODO("Not yet implemented")
    }

    override fun saveReservationTicket(ticket: Ticket): Long {
        TODO("Not yet implemented")
    }

    override fun clearReservationTickets() {
        TODO("Not yet implemented")
    }

    override fun findReservationTicket(ticketId: Long): ReservationTicket? {
        TODO("Not yet implemented")
    }
}
