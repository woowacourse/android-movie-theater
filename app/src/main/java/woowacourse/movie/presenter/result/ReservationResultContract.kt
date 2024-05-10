package woowacourse.movie.presenter.result

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

interface ReservationResultContract {
    interface View {
        fun showReservationMovieTitle(movie: Movie)

        fun showReservationTicketInfo(ticket: Ticket)

        fun finshActivityWithErrorToast()

        fun showReservationTheaterName(theaterName: String)
    }

    interface Presenter {
        fun loadTicketWithTicketId(ticketId: Long)

        fun loadMovie(movieId: Int)

        fun loadTicket(ticket: Ticket)

        fun loadTheater(theaterId: Int)
    }
}
