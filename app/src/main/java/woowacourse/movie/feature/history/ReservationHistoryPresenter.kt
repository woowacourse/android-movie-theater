package woowacourse.movie.feature.history

import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDao
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val ticketDao: TicketDao,
) : ReservationHistoryContract.Presenter {
    private lateinit var tickets: List<Ticket>

    override fun loadTicket() {
        // TODO 임시값 Room 데이터로 변경 후 제거
        thread {
            tickets = ticketDao.findAll()
        }.join()
        view.showReservationHistory(tickets)
    }
}
