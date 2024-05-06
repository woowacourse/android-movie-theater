package woowacourse.movie.feature.finished

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.ticket.Ticket

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val screeningDao: ScreeningDao,
    private val ticket: Ticket,
) : ReservationFinishedContract.Presenter {
    override fun handleUndeliveredTicket() {
        if (ticket.movieId == DEFAULT_MOVIE_ID) {
            view.showErrorSnackBar()
        }
    }

    override fun loadMovie() {
        val movie: Movie = screeningDao.find(ticket.movieId)
        view.showMovieTitle(movie)
    }

    override fun loadTicket() {
        view.showReservationHistory(ticket)
    }
}
