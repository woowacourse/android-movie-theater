package woowacourse.movie.presentation.ticket.detail

import woowacourse.movie.domain.model.Ticket

class TicketDetailPresenter(
    private val view: TicketDetailContract.View,
    private val ticket: Ticket,
) : TicketDetailContract.Presenter {
    override fun loadBookingResult() {
        view.showTicketInfo(ticket)
    }
}
