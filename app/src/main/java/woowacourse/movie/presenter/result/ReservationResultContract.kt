package woowacourse.movie.presenter.result

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.model.ticket.Ticket

interface ReservationResultContract {
    interface View {
        fun showMovieTitle(movie: Movie)

        fun showReservationHistory(ticket: Ticket)

        fun finshActivityWithErrorToast()

        fun showTheaterName(theaterName: String)
    }

    interface Presenter {
        fun loadTicketWithTicketId(ticketId: Long)

        fun loadMovie(movieId: Int)

        fun loadTicket(ticket: Ticket)

        fun loadTheater(theaterId: Int)
    }
}
