package woowacourse.app.presentation.ui.booking

import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.ScreeningDate
import woowacourse.domain.ticket.TicketCount

class BookingPresenter(
    private val bookingView: BookingContract.View,
    override val movie: Movie,
) : BookingContract.Presenter {
    private var ticketCount: TicketCount = TicketCount()

    override fun restoreTicketCount(value: Int) {
        ticketCount = TicketCount(value)
    }

    override fun initTicketCount() {
        bookingView.showTicketCount(ticketCount.value)
    }

    override fun subTicket() {
        ticketCount = ticketCount.minus()
        bookingView.showTicketCount(ticketCount.value)
    }

    override fun addTicket() {
        ticketCount = ticketCount.plus()
        bookingView.showTicketCount(ticketCount.value)
    }

    override fun initDateTimes() {
        bookingView.initDateTimes(movie.screeningDates)
    }

    override fun fetchScreeningTimes() {
        bookingView.initScreeningTimes { ScreeningDate(it).screeningTimes }
    }

    override fun completeBooking() {
        bookingView.startSeatActivity(ticketCount.value)
    }

    override fun getTicketCount(): Int {
        return ticketCount.value
    }
}
