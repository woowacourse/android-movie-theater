package woowacourse.movie.presentation.ticket

import woowacourse.movie.data.repository.DefaultTicketRepository
import woowacourse.movie.data.repository.TicketRepository
import woowacourse.movie.domain.model.Ticket

class TicketListPresenter(
    private val view: TicketListContract.View,
    private val ticketRepository: TicketRepository = DefaultTicketRepository(),
) : TicketListContract.Presenter {
    override fun loadTicketList() {
        view.showTicketList(ticketRepository.fetch())
    }

    override fun selectTicket(ticket: Ticket) {
        view.navigateToTicketDetail(ticket)
    }
}
