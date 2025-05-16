package woowacourse.movie.data.ticket

import woowacourse.movie.domain.model.ticket.Ticket

class TicketRepositoryImpl(private val ticketDao: TicketDao) : TicketRepository {
    override fun insert(ticket: Ticket) {
        ticketDao.insert(ticket.toEntity())
    }

    override fun getAll(): List<Ticket> {
        return ticketDao.getAll().map { ticketEntity -> ticketEntity.toDomain() }
    }
}
