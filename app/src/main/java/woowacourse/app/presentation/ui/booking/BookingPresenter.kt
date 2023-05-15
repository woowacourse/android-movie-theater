package woowacourse.app.presentation.ui.booking

import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.ScreeningDate
import woowacourse.domain.ticket.TicketCount

class BookingPresenter(
    private val bookingView: BookingContract.View,
    private val movie: Movie,
) : BookingContract.Presenter {
    private var ticketCount: TicketCount = TicketCount()

    override fun restoreTicketCount(value: Int) {
        ticketCount = TicketCount(value)
    }

    override fun initView() {
        bookingView.showTicketCount(ticketCount.value)
        bookingView.initDateTimes(movie.screeningDates)
        bookingView.showMovie(movie)
    }

    override fun subTicket() {
        ticketCount = ticketCount.minus()
        bookingView.showTicketCount(ticketCount.value)
    }

    override fun addTicket() {
        ticketCount = ticketCount.plus()
        bookingView.showTicketCount(ticketCount.value)
    }

    override fun fetchScreeningTimes() {
        bookingView.initScreeningTimes { ScreeningDate(it).screeningTimes }
    }

    override fun completeBooking() {
        bookingView.startSeatActivity(ticketCount.value, movie)
    }

    override fun getTicketCount(): Int {
        return ticketCount.value
    }
}
