package woowacourse.movie.presentation.ticketingResult

import woowacourse.movie.model.Ticket
import woowacourse.movie.repository.TheaterListRepository

class TicketingResultPresenter(
    private val ticketingResultView: TicketingResultContract.View,
    private val theaterListRepository: TheaterListRepository,
) : TicketingResultContract.Presenter {
    override fun loadTicketInfo(movieTicket: Ticket?) {
        movieTicket?.let { ticket ->
            ticketingResultView.displayTicketInfo(ticket, findTheaterName(ticket.theaterId))
        }
    }

    private fun findTheaterName(theaterId: Long): String = theaterListRepository.findTheaterOrNull(theaterId)?.name ?: ""
}
