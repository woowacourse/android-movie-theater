package woowacourse

import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.repository.ReservationTicketRepository

class MockReservationTicketRepository : ReservationTicketRepository {
    override fun loadReservationTickets(): List<ReservationTicket> = listOf()

    override fun saveReservationTicket(ticket: Ticket): Long = 0L

    override fun findReservationTicket(ticketId: Long): ReservationTicket? = null
}
