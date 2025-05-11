package woowacourse.movie.presentation.ticket.list

import woowacourse.movie.domain.model.Ticket

interface TicketListContract {
    interface View {
        fun showTicketList(items: List<Ticket>)

        fun navigateToTicketDetail(ticket: Ticket)
    }

    interface Presenter {
        fun loadTicketList()

        fun selectTicket(ticket: Ticket)
    }
}
