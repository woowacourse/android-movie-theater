package woowacourse.movie.feature.finished

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
) : ReservationFinishedContract.Presenter {
    override fun loadMovie(movieId: Int) {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun loadTicket(ticket: Ticket) {
        view.showReservationHistory(ticket)
    }

    override fun loadTheater(theaterId: Int) {
        val theater = theaterDao.find(theaterId)
        view.showTheaterName(theater.name)
    }
}
