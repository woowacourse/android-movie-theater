package woowacourse.movie.view.history

import woowacourse.movie.domain.model.ticket.Ticket

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {
    override fun loadTickets(tickets: List<Ticket>) {
        view.showTickets(tickets)
    }

    override fun selectHistory(ticket: Ticket) {
        view.moveToBookingComplete(ticket)
    }
}
