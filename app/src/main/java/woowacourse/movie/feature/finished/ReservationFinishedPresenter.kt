package woowacourse.movie.feature.finished

import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDao
import kotlin.concurrent.thread

class ReservationFinishedPresenter(
    private val view: ReservationFinishedContract.View,
    private val ticketId: Long,
    private val ticketDao: TicketDao,
) : ReservationFinishedContract.Presenter {
    private lateinit var ticket: Ticket

    override fun loadTicket() {
        thread {
            ticket = ticketDao.find(ticketId)
        }.join()
        if (ticketId != 0L) {
            loadReservationInformation()
        } else {
            handleUndeliveredTicket()
        }
    }

    private fun loadReservationInformation() {
        view.showReservationHistory(ticket)
    }

    private fun handleUndeliveredTicket() {
        view.showErrorSnackBar()
    }
}
