package woowacourse.movie.feature.history

import android.content.Context
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.TicketRoomRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    applicationContext: Context,
    private val ticketRepository: TicketRepository
    = TicketRoomRepository(TicketDatabase.instance(applicationContext).ticketDao())
) :
    ReservationHistoryContract.Presenter {
    override fun loadTickets() {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        view.displayTickets(tickets)
    }
}
