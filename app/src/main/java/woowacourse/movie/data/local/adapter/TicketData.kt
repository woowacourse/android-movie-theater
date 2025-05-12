package woowacourse.movie.data.local.adapter

import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.data.TicketDataAdapter

class TicketData(private val dao: TicketDao) : TicketDataAdapter {
    override fun insert(ticket: Ticket) = dao.insert(ticket.toTicketEntity())

    override fun getTicket(id: Long): Ticket = dao.getTicket(id).toTicket()

    override fun getAll(): List<Ticket> = dao.getAll().map { it.toTicket() }

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
        Ticket(
            id = id,
            title = title,
            count = count,
            showtime = showtime,
            cinemaName = cinemaName,
            seats = seats,
            purchaseType = purchaseType,
        )
}
