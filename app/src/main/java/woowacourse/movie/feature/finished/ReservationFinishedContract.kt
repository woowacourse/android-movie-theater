package woowacourse.movie.feature.finished

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

interface ReservationFinishedContract {
    interface View {
        fun showMovieTitle(movie: Movie)

        fun showReservationHistory(ticket: Ticket)

        fun showErrorSnackBar()

        fun navigateToHome()
    }

    interface Presenter {
        fun loadTicket()
    }
}
