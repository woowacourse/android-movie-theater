package woowacourse.movie.presentation.result

import woowacourse.movie.domain.model.Ticket

class BookingResultPresenter(
    private val view: BookingResultContract.View,
    private val ticket: Ticket,
) : BookingResultContract.Presenter {
    override fun loadBookingResult() {
        view.showTicketInfo(ticket)
    }
}
