package woowacourse.movie.domain.datasource

import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.domain.ticket.TicketHistory

interface TicketDataSource {
    fun insert(ticket: Ticket): Long

    fun getTicket(id: Long): TicketHistory

    fun getAll(): List<TicketHistory>
}
