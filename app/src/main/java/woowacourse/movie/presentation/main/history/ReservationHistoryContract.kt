package woowacourse.movie.presentation.main.history

import woowacourse.movie.domain.model.movieticket.MovieTicket

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistory(movieTickets: List<MovieTicket>)

        fun navigateToReservationActivity(movieTicketId: Long)
    }

    interface Presenter {
        fun loadReservationHistory()

        fun itemClicked(movieTicketId: Long)
    }
}
