package woowacourse.movie.view.history

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.domain.model.ticket.Ticket
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val repository: TicketRepository,
) : BookingHistoryContract.Presenter {
    override fun loadTickets() {
        thread {
            view.showTickets(repository.getAll())
        }
    }

    override fun selectHistory(ticket: Ticket) {
        view.moveToBookingComplete(ticket)
    }
}
