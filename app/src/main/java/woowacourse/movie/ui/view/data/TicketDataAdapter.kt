package woowacourse.movie.ui.view.data

import woowacourse.movie.domain.ticket.Ticket

interface TicketDataAdapter {
    fun insert(ticket: Ticket): Long

    fun getTicket(id: Long): Ticket

    fun getAll(): List<Ticket>
}
