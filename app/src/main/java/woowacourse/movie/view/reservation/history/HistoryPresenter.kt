package woowacourse.movie.view.reservation.history

import androidx.fragment.app.Fragment
import woowacourse.movie.data.TicketRepository
import woowacourse.movie.domain.Ticket
import kotlin.concurrent.thread

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val repository: TicketRepository,
) : HistoryContract.Presenter {
    override fun loadTickets() {
        thread {
            val tickets = repository.loadTickets()
            (view as? Fragment)?.activity?.runOnUiThread {
                view.showTickets(tickets)
            }
        }
    }

    override fun onTicketClicked(ticket: Ticket) {
        view.navigateToReservationComplete(ticket)
    }
}
