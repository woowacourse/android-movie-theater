package woowacourse.movie.presentation.bookinghistory

import woowacourse.movie.data.bookinghistory.BookingHistoryRepository
import woowacourse.movie.domain.model.movie.MovieTicket

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val bookingHistoryRepository: BookingHistoryRepository,
) : BookingHistoryContract.Presenter {
    override fun loadBookingHistory() {
        bookingHistoryRepository.getBookings { movieTickets ->
            view.showBookingHistory(movieTickets)
        }
    }

    override fun selectBookingHistory(ticket: MovieTicket) {
        view.navigateToBookingSummary(ticket)
    }
}
