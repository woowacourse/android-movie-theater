package woowacourse.movie.data.ticket

import woowacourse.movie.domain.model.ticket.Ticket

interface TicketRepository {
    fun insert(ticket: Ticket)

    fun getAll(): List<Ticket>
}
