package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.tools.TicketCount

class BookingPresenter(
    override val view: BookingContract.View,
    private var ticketCount: TicketCount = TicketCount()
) : BookingContract.Presenter {

    init {
        view.setTicketCount(ticketCount.value)
    }

    override fun plusTicketCount() {
        ticketCount = ticketCount.plus()
        view.setTicketCount(ticketCount.value)
    }

    override fun minusTicketCount() {
        ticketCount = ticketCount.minus()
        view.setTicketCount(ticketCount.value)
    }

    override fun getTicketCurrentCount(): Int = ticketCount.value
}
