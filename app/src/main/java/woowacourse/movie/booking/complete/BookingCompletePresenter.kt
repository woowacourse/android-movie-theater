package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    override fun initializeData(ticket: TicketUiModel) {
        view.showBookingCompleteResult(ticket)
    }
}
