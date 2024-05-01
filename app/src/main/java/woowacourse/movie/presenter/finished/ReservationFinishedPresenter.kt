package woowacourse.movie.presenter.finished

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val dao: ScreeningDao,
) : ReservationFinishedContract.Presenter {
    override fun loadMovie(movieId: Int) {
        val movie: Movie = dao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun loadTicket(ticket: Ticket) {
        view.showReservationHistory(ticket)
    }
}
