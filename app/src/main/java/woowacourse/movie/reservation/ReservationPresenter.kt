package woowacourse.movie.reservation

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import woowacourse.movie.database.AppDatabase

class ReservationPresenter(
    private val database: AppDatabase
) : ReservationContract.Presenter {

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
}
