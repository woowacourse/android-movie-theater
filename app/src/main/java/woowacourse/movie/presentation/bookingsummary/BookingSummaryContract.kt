package woowacourse.movie.presentation.bookingsummary

import woowacourse.movie.domain.model.movie.MovieTicket

interface BookingSummaryContract {
    interface View {
        fun showTicket(ticket: MovieTicket)

        fun showCancelableTime(cancelableTime: Int)
    }

    interface Presenter {
        fun initializeBookingSummary(movieTicket: MovieTicket)
    }
}
