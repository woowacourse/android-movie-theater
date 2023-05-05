package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.model.MovieTicketModel

interface ReservationContract {
    interface View {
        val presenter: Presenter
        var reservationTicket: List<MovieTicketModel>
        fun setTextOnEmptyState(isEmpty: Boolean)
    }

    interface Presenter {
        fun getReservationTickets()
        fun isEmptyMovieReservation()
    }
}
