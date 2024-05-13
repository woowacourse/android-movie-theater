package woowacourse.movie.list.presenter

import androidx.fragment.app.Fragment
import woowacourse.movie.database.TicketDatabase
import woowacourse.movie.list.contract.ReservationHistoryContract
import woowacourse.movie.ticket.model.TicketEntity
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    private lateinit var tickets: List<TicketEntity>
    override fun loadDataFromDb() {
        val context = (view as Fragment).requireContext()
        val ticketDb = TicketDatabase.getDatabase(context)
        thread {
            tickets = ticketDb.ticketDao().getAll()
        }.join()
        view.showData(tickets)
    }
}
