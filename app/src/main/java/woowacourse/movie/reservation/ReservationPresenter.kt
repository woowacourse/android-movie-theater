package woowacourse.movie.reservation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val database: AppDatabase
) :
    ReservationContract.Presenter {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun detachView() {
        coroutineScope.cancel()
    }

    override fun loadData() {
        thread {
            val ticket = database.ticketDao().getAllTickets()
            view.showReservations(ticket)
        }.start()
    }

    override fun onClickedList(ticket: Ticket) {
        view.navigateToTicketDetail(ticket)
    }
}
