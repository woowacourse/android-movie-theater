package woowacourse.movie.booking.complete

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: Ticket

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket.toDomain()

        view.showBookingCompleteResult(ticket)
    }
}
