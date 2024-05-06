package woowacourse.movie.repository

import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket

interface ReservationTicketRepository {

    fun loadReservationTickets(): List<ReservationTicket>

    fun saveReservationTicket(ticket: Ticket)

    fun clearReservationTickets()
}
