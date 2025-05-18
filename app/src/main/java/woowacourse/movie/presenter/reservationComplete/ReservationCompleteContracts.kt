package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.model.ticket.MovieTicket

interface ReservationCompleteContracts {
    interface View {
        fun showMovieTicket(movieTicket: MovieTicket)
    }

    interface Presenter {
        fun fetchTicketData(reservationDetailId: Long)
    }
}
