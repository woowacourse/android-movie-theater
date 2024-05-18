package woowacourse.movie.feature.history

import woowacourse.movie.db.ticket.TicketDao
import woowacourse.movie.db.ticket.TicketEntity.Companion.toDomain
import woowacourse.movie.model.ticket.Ticket
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val ticketDao: TicketDao,
) : ReservationHistoryContract.Presenter {
    private lateinit var tickets: List<Ticket>

    override fun loadTicket() {
        thread {
            tickets = ticketDao.findAll().map { it.toDomain() }
        }.join()
        view.showReservationHistory(tickets)
    }

    override fun deliverTicketId(ticketId: Long?) {
        view.navigateToReservationInformation(ticketId)
    }
}
