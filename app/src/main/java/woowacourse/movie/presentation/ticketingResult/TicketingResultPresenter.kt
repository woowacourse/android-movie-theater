package woowacourse.movie.presentation.ticketingResult

import woowacourse.movie.model.Ticket
import woowacourse.movie.repository.ReservationRepository
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

    override fun loadTicket(
        ticketId: Long,
        reservationRepository: ReservationRepository,
    ): Ticket? {
        return reservationRepository.findTicket(ticketId)
    }

    private fun findTheaterName(theaterId: Long): String = theaterListRepository.findTheaterOrNull(theaterId)?.name ?: ""
}
