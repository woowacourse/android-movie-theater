package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.model.ticket.MovieTicket

interface ReservationCompleteContracts {
    interface View {
        fun showMovieTicket(movieTicket: MovieTicket)

        fun showErrorDialogMessage()
    }

    interface Presenter {
        fun updateTicketData(movieTicket: MovieTicket)

        fun requestErrorDialogMessage()
    }
}
