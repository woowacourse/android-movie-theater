package woowacourse.movie.feature.finished

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val screeningDao: ScreeningDao,
) : ReservationFinishedContract.Presenter {
    override fun loadMovie(movieId: Int) {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun loadTicket(ticket: Ticket) {
        view.showReservationHistory(ticket)
    }
}
