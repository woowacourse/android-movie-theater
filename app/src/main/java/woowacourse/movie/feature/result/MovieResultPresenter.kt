package woowacourse.movie.feature.result

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class MovieResultPresenter(private val view: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadTicket(
        ticketRepository: TicketRepository,
        ticketId: Long,
    ) {
        var result: Result<Ticket>? = null
        thread {
            result = runCatching { ticketRepository.find(ticketId) }
        }.join()
        result
            ?.onFailure { view.showToastInvalidMovieIdError(it) }
            ?.onSuccess { view.displayTicket(it) }
    }
}
