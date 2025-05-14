package woowacourse.movie.view.history

import woowacourse.movie.domain.datasource.TicketDataSource
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
}
