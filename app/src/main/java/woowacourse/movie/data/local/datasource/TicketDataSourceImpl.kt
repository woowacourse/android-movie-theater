package woowacourse.movie.data.local.datasource

import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.domain.ticket.TicketHistory
import kotlin.concurrent.thread

class TicketDataSourceImpl(private val dao: TicketDao) :
    TicketDataSource {
    override fun insert(
        ticket: Ticket,
        onComplete: (ticketId: Long) -> Unit,
    ) {
        thread {
            val ticketId = dao.insert(ticket.toTicketEntity())
            onComplete(ticketId)
        }
    }

    override fun getTicket(
        id: Long,
        onComplete: (ticket: TicketHistory) -> Unit,
    ) {
        thread {
            val ticket = dao.getTicket(id).toTicket()
            onComplete(ticket)
        }
    }

    override fun getAll(onComplete: (List<TicketHistory>) -> Unit) {
        thread {
            val allTickets = dao.getAll().map { it.toTicket() }
            onComplete(allTickets)
        }
    }

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
