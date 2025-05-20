package woowacourse.movie.domain.datasource

import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.domain.ticket.TicketHistory

interface TicketDataSource {
    fun insert(
        ticket: Ticket,
        onComplete: (ticketId: Long) -> Unit,
    )

    fun getTicket(
        id: Long,
        onComplete: (ticket: TicketHistory) -> Unit,
    )

    fun getAll(onComplete: (List<TicketHistory>) -> Unit)
}
