package woowacourse.movie.data.local.datasource

import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
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
            seats = toSeatSet(seats),
            purchaseType = purchaseType.name,
        )

    private fun TicketEntity.toTicket() =
        TicketHistory(
            id = id,
            title = title,
            count = count,
            showtime = showtime,
            cinemaName = cinemaName,
            seats = fromSeatSet(seats),
            purchaseType = PurchaseType.of(purchaseType),
        )

    private fun fromSeatSet(value: String): Set<Seat> {
        return value.split(";").mapNotNull { pair ->
            val parts = pair.split(",")
            if (parts.size == 2) {
                val row = parts[0].toIntOrNull()
                val col = parts[1].toIntOrNull()
                if (row != null && col != null) Seat.invoke(row, col) else null
            } else {
                null
            }
        }.toSet()
    }

    private fun toSeatSet(seats: Set<Seat>): String {
        return seats.joinToString(";") { "${it.row.value},${it.column.value}" }
    }
}
