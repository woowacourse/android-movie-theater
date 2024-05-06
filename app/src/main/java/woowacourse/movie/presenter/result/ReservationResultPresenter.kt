package woowacourse.movie.presenter.result

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
) : ReservationResultContract.Presenter {
    override fun loadMovie(movieId: Int) {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun loadTicket(ticket: Ticket) {
        view.showReservationHistory(ticket)
    }

    override fun loadTheater(theaterId: Int) {
        val theater = theaterDao.find(theaterId)
        view.showTheaterName(theater.theaterName)
    }
}
