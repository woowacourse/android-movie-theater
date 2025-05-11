package woowacourse.movie.view.reservation.history

import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.domain.TicketRepository
import woowacourse.movie.view.reservation.Ticket
import kotlin.concurrent.thread

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val ticketRepository: TicketRepository = TicketRepositoryImpl(),
) : HistoryContract.Presenter {
    private var tickets: List<Ticket> = emptyList()

    override fun loadTickets() {
        thread {
            tickets = ticketRepository.getAll()
            view.showMoviesScreen(tickets)
        }
    }

    override fun onTicketSelected(index: Int) {
        if (index in tickets.indices) {
            view.handleReservationComplete(tickets[index])
        }
    }
}
