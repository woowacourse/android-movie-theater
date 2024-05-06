package woowacourse.movie.presenter.finished

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

interface ReservationFinishedContract {
    interface View {
        fun showMovieTitle(movie: Movie)

        fun showReservationHistory(ticket: Ticket)

        fun showErrorToast()

        fun showTheaterName(theaterName: String)
    }

    interface Presenter {
        fun loadMovie(movieId: Int)

        fun loadTicket(ticket: Ticket)

        fun loadTheater(theaterId: Int)
    }
}
