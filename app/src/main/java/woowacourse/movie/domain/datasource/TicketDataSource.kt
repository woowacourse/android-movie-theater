package woowacourse.movie.domain.datasource

import woowacourse.movie.domain.ticket.Ticket

interface TicketDataSource {
    fun insert(ticket: Ticket): Long

    fun getTicket(id: Long): Ticket

    fun getAll(): List<Ticket>
}
