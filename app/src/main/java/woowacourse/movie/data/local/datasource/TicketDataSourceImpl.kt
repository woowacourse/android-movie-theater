package woowacourse.movie.data.local.datasource

import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.domain.ticket.TicketHistory

class TicketDataSourceImpl(private val dao: TicketDao) :
    TicketDataSource {
    override fun insert(ticket: Ticket) = dao.insert(ticket.toTicketEntity())

    override fun getTicket(id: Long): TicketHistory = dao.getTicket(id).toTicket()

    override fun getAll(): List<TicketHistory> = dao.getAll().map { it.toTicket() }

    private fun Ticket.toTicketEntity() =
        TicketEntity(
            title = title,
            count = count,
            showtime = showtime,
            cinemaName = cinemaName,
            seats = seats,
            purchaseType = purchaseType,
        )

    private fun TicketEntity.toTicket() =
        TicketHistory(
            id = id,
            title = title,
            count = count,
            showtime = showtime,
            cinemaName = cinemaName,
            seats = seats,
            purchaseType = purchaseType,
        )
}
