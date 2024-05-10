package woowacourse.movie.feature.history

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class ReservationHistoryPresenter(private val view: ReservationHistoryContract.View) :
    ReservationHistoryContract.Presenter {
    override fun loadTickets(ticketRepository: TicketRepository) {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        view.displayTickets(tickets)
    }
}
