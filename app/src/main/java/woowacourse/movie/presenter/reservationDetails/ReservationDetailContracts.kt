package woowacourse.movie.presenter.reservationDetails

import woowacourse.movie.model.ticket.MovieTicket

interface ReservationDetailContracts {
    interface View {
        fun showReservations(reservations: List<MovieTicket>)

        fun showReservationCompleteView(movieTicket: MovieTicket)
    }

    interface Presenter {
        fun loadReservations()

        fun requestReservationComplete(ticketId: Long)
    }
}
