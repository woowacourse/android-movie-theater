package woowacourse.movie.view.history

import android.content.Context
import woowacourse.movie.data.db.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.model.datasource.TicketDataSource
import woowacourse.movie.view.core.util.DefaultMainThreadExecutor
import woowacourse.movie.view.core.util.MainThreadExecutor
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val dataSource: TicketDataSource,
    private val mainThreadExecutor: MainThreadExecutor,
) : BookingHistoryContract.Presenter {
    override fun loadHistory() {
        thread {
            val tickets = dataSource.readAllTicket()
            mainThreadExecutor.execute {
                view.showTickets(tickets)
            }
        }
    }

    companion object {
        fun initialize(
            view: BookingHistoryContract.View,
            context: Context,
        ): BookingHistoryContract.Presenter {
            val dao = UserDatabase.getDatabase(context).ticketDao()
            val dataSource = TicketDataSourceImpl(dao)
            val executor = DefaultMainThreadExecutor()
            return BookingHistoryPresenter(view, dataSource, executor)
        }
    }
}
