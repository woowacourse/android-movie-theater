package woowacourse.movie.view.complete

import woowacourse.movie.domain.model.Ticket

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val ticket: Ticket,
) : BookingCompleteContract.Presenter {
    init {
        loadTicket()
    }

    override fun loadTicket() {
        view.showTicket(ticket)
    }
}
