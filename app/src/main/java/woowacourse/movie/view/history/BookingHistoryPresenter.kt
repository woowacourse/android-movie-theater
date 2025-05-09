package woowacourse.movie.view.history

import android.content.Context
import woowacourse.movie.data.db.TicketDataSource
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val dataSource: TicketDataSource,
) : BookingHistoryContract.Presenter {
    override fun loadHistory() {
        val result = mutableListOf<Ticket>()
        thread {
            val tickets = dataSource.readAllTicket()
            result.addAll(tickets)
        }.join()
        view.showTickets(result)
    }

    companion object {
        fun initialize(
            view: BookingHistoryContract.View,
            context: Context,
        ): BookingHistoryContract.Presenter {
            val dao = UserDatabase.getDatabase(context).ticketDao()
            val dataSource = TicketDataSource(dao)
            return BookingHistoryPresenter(view, dataSource)
        }
    }
}
