package woowacourse.movie.feature.result

import android.content.Context
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.TicketRoomRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class MovieResultPresenter(
    private val view: MovieResultContract.View,
    applicationContext: Context,
    private val ticketRepository: TicketRepository
    = TicketRoomRepository(TicketDatabase.instance(applicationContext).ticketDao())
) :
    MovieResultContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        var result: Result<Ticket>? = null
        thread {
            result = runCatching { ticketRepository.find(ticketId) }
        }.join()
        result
            ?.onFailure { view.showToastInvalidMovieIdError(it) }
            ?.onSuccess { ticket ->
                val movie = MovieRepositoryImpl.getMovieById(ticket.movieId)
                view.displayTicket(ticket, movie)
            }
    }
}
