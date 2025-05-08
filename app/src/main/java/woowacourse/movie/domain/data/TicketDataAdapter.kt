package woowacourse.movie.domain.data

import woowacourse.movie.domain.ticket.Ticket

interface TicketDataAdapter {
    fun insert(ticket: Ticket)
}
