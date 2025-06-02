package woowacourse.movie.data

import woowacourse.movie.domain.Ticket

class TicketRepository(private val ticketInfoDao: TicketInfoDao) {
    fun saveTicket(ticket: Ticket) {
        ticketInfoDao.insert(ticket.toEntity())
    }

    fun loadTickets(): List<Ticket> {
        return ticketInfoDao.getAll().map { it.toDomain() }
    }
}
