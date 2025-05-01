package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: TicketUiModel

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket
        view.showBookingCompleteResult(ticket)
    }
}
