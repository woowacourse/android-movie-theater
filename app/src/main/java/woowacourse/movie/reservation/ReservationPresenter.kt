package woowacourse.movie.reservation

import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val database: AppDatabase,
) :
    ReservationContract.Presenter {
    override fun loadData() {
        thread {
            val ticket = database.ticketDao().getAllTickets()
            view.showReservations(ticket)
        }
    }

    override fun navigateDetailView(ticket: Ticket) {
        view.navigateToTicketDetail(ticket)
    }
}
