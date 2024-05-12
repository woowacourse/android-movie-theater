package woowacourse.movie.feature.finished

import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.Ticket.Companion.DEFAULT_TICKET_ID
import woowacourse.movie.db.ticket.TicketDao
import kotlin.concurrent.thread

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val ticketId: Long,
    private val ticketDao: TicketDao,
) : ReservationFinishedContract.Presenter {
    private lateinit var ticket: Ticket

    override fun loadTicket() {
        if (ticketId != DEFAULT_TICKET_ID) {
            thread {
                ticket = ticketDao.find(ticketId)
            }.join()
            view.showReservationHistory(ticket)
            view.notifyScreeningTime(ticket)
        } else {
            view.showErrorSnackBar()
        }
    }
}
