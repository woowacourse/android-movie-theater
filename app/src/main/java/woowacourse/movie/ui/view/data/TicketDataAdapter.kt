package woowacourse.movie.ui.view.data

import woowacourse.movie.domain.ticket.Ticket

interface TicketDataAdapter {
    fun insert(ticket: Ticket)
}
