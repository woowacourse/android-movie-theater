package woowacourse.movie.ticket.contract

import woowacourse.movie.ticket.model.MovieTicket

interface MovieTicketContract {
    interface View {
        fun showTicketData(movieTicket: MovieTicket)

        fun showMessage(message: String)
    }

    interface Presenter {
        fun loadReservationDetails()
    }
}
