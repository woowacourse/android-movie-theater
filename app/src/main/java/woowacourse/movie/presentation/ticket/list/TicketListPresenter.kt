package woowacourse.movie.presentation.ticket.list

import woowacourse.movie.data.repository.TicketRepository
import woowacourse.movie.domain.model.Ticket

class TicketListPresenter(
    private val view: TicketListContract.View,
    private val ticketRepository: TicketRepository,
) : TicketListContract.Presenter {
    override fun loadTicketList() {
        view.showTicketList(ticketRepository.getAll())
    }

    override fun selectTicket(ticket: Ticket) {
        view.navigateToTicketDetail(ticket)
    }
}
