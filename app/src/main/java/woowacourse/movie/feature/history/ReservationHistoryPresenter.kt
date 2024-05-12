package woowacourse.movie.feature.history

import android.content.Context
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.RoomTicketRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    applicationContext: Context,
    private val ticketRepository: TicketRepository
    = RoomTicketRepository(TicketDatabase.instance(applicationContext).ticketDao())
) :
    ReservationHistoryContract.Presenter {
    override fun loadTickets() {
        val tickets = ticketRepository.findAll()
        view.displayTickets(tickets)
    }
}
