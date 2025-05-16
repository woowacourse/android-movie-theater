package woowacourse.movie.data.ticket

import woowacourse.movie.domain.model.ticket.Ticket

class TicketRepository(private val ticketDao: TicketDao) {
    fun insert(ticket: Ticket) {
        ticketDao.insert(ticket.toEntity())
    }

    fun getAll(): List<Ticket> {
        return ticketDao.getAll().map { ticketEntity -> ticketEntity.toDomain() }
    }
}
