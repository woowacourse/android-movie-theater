package woowacourse.movie.feature.finished

import woowacourse.movie.db.ticket.TicketDao
import woowacourse.movie.db.ticket.TicketEntity.Companion.toDomain
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.model.ticket.Ticket.Companion.DEFAULT_TICKET_ID
import kotlin.concurrent.thread

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val ticketId: Long,
    private val ticketDao: TicketDao,
) : ReservationFinishedContract.Presenter {
    private lateinit var ticket: Ticket

    override fun loadTicket() {
        if (isValidTicketId()) {
            thread {
                ticket = ticketDao.find(ticketId).toDomain()
            }.join()
            view.showReservationHistory(ticket)
            view.notifyScreeningTime(ticket)
        } else {
            view.showErrorSnackBar()
        }
    }

    private fun isValidTicketId(): Boolean {
        return ticketId != DEFAULT_TICKET_ID
    }
}
