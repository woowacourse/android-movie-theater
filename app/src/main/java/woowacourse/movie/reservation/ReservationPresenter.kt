package woowacourse.movie.reservation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket

class ReservationPresenter() : ReservationContract.Presenter {
    private var view: ReservationContract.View? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun attachView(view: ReservationContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
        coroutineScope.cancel()
    }

    override fun loadData(database: AppDatabase) {
        Thread {
            val ticket = database.ticketDao().getAllTickets()
            view?.showReservations(ticket)
        }.start()
    }

    override fun onClickedList(ticket: Ticket) {
        view?.navigateToTicketDetail(ticket)
    }
}
