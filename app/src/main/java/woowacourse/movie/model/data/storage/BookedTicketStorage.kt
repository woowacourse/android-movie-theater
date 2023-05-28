package woowacourse.movie.model.data.storage

import woowacourse.movie.domain.model.tools.Ticket
import woowacourse.movie.presentation.model.TicketModel

interface BookedTicketStorage {
    fun getBookedTickets(): List<TicketModel>

    fun addBookedTicket(ticket: Ticket)
}
