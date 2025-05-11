package woowacourse.movie.view.reservation.history

import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.TicketRepository
import woowacourse.movie.view.reservation.toUi
import kotlin.concurrent.thread

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val ticketRepository: TicketRepository = TicketRepositoryImpl(),
) : HistoryContract.Presenter {
    private var tickets: List<Ticket> = emptyList()

    override fun loadTickets() {
        thread {
            tickets = ticketRepository.getAll()
            val ticketUis = tickets.map { it.toUi() }
            view.showMoviesScreen(ticketUis)
        }
    }

    override fun onTicketSelected(index: Int) {
        if (index in tickets.indices) {
            view.handleReservationComplete(tickets[index].toUi())
        }
    }
}
