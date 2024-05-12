package woowacourse.movie.feature.result

import android.content.Context
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.ticket.RoomTicketRepository
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository

class MovieResultPresenter(
    private val view: MovieResultContract.View,
    applicationContext: Context,
    private val ticketRepository: TicketRepository =
        RoomTicketRepository(TicketDatabase.instance(applicationContext).ticketDao()),
) :
    MovieResultContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        runCatching { ticketRepository.find(ticketId) }
            .onFailure { view.showToastInvalidMovieIdError(it) }
            .onSuccess { ticket ->
                val movie = MovieRepositoryImpl.getMovieById(ticket.movieId)
                view.displayTicket(ticket, movie)
            }
    }
}
