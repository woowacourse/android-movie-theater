package woowacourse.movie.ui.booking

import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ticket.TicketCount

class BookingPresenter(
    private val bookingView: BookingContract.View,
    override val movie: MovieUiModel
) : BookingContract.Presenter {

    override var ticketCount: TicketCount = TicketCount()

    override fun initTicketCount() {
        bookingView.setTicketCountText(ticketCount.value)
    }

    override fun minusTicketCount() {
        ticketCount = ticketCount.minus()

        bookingView.setTicketCountText(ticketCount.value)
    }

    override fun plusTicketCount() {
        ticketCount = ticketCount.plus()

        bookingView.setTicketCountText(ticketCount.value)
    }
}
