package woowacourse.movie.feature.result

import woowacourse.movie.data.TicketRepository

class MovieResultPresenter(private val view: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadTicket(
        ticketRepository: TicketRepository,
        ticketId: Long,
    ) {
        Thread {
            val ticket =
                runCatching {
                    ticketRepository.find(ticketId)
                }.getOrElse {
                    view.showToastInvalidMovieIdError(it)
                    return@Thread
                }
            view.displayTicket(ticket)
        }.start()
    }
}
