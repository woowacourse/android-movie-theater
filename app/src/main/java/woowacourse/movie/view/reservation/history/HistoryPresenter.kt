package woowacourse.movie.view.reservation.history

import woowacourse.movie.TicketProvider
import woowacourse.movie.data.mapper.toUi
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.TicketRepository
import kotlin.concurrent.thread

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val ticketRepository: TicketRepository,
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

    companion object {
        fun provideFactory(
            view: HistoryContract.View,
            repository: TicketRepository = TicketProvider.ticketRepository,
        ): HistoryContract.Presenter = HistoryPresenter(view, repository)
    }
}
