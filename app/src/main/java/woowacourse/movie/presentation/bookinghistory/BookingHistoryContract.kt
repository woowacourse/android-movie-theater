package woowacourse.movie.presentation.bookinghistory

import woowacourse.movie.domain.model.movie.MovieTicket

interface BookingHistoryContract {
    interface View {
        fun showBookingHistory(tickets: List<MovieTicket>)

        fun navigateToBookingSummary(ticket: MovieTicket)
    }

    interface Presenter {
        fun loadBookingHistory()

        fun selectBookingHistory(ticket: MovieTicket)
    }
}
