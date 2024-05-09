package woowacourse.movie.presenter.result

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.repository.ReservationTicketRepository

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
    private val repository: ReservationTicketRepository,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
) : ReservationResultContract.Presenter {
    override fun loadTicketWithTicketId(ticketId: Long) {
        Thread {
            runCatching {
                val reservationTicket = repository.findReservationTicket(ticketId)
                reservationTicket ?: throw NoSuchElementException()
            }.onSuccess {
                loadMovie(it.movieId)
                loadTicket(it.toTicket())
            }.onFailure {
                view.finshActivityWithErrorToast()
            }
        }.start()
    }

    override fun loadMovie(movieId: Int) {
        val movie: Movie = screeningDao.find(movieId)
        view.showReservationMovieTitle(movie)
    }

    override fun loadTicket(ticket: Ticket) {
        view.showReservationTicketInfo(ticket)
    }

    override fun loadTheater(theaterId: Int) {
        val theater = theaterDao.find(theaterId)
        view.showReservationTheaterName(theater.theaterName)
    }
}
