package woowacourse.movie.presentation.ticket

import woowacourse.movie.domain.model.movieticket.MovieTicket

interface MovieTicketContract {
    interface View {
        fun showTicketData(movieTicket: MovieTicket)

        fun showMessage(message: String)
    }

    interface Presenter {
        fun loadReservationDetails()
    }
}
