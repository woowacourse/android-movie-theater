package woowacourse.movie.presentation.bookinghistory

import woowacourse.movie.domain.model.movie.MovieTicket

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View
) : BookingHistoryContract.Presenter {
    override fun loadBookingHistory() {
        TODO("Not yet implemented")
    }

    override fun selectBookingHistory(ticket: MovieTicket) {
        view.navigateToBookingSummary(ticket)
    }
}