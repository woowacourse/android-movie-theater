package woowacourse.movie.view.history

import woowacourse.movie.domain.Callback
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.model.Ticket

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val dataSource: TicketDataSource,
) : BookingHistoryContract.Presenter {
    override fun loadHistory() {
        dataSource.readAllTicket(
            object : Callback<List<Ticket>> {
                override fun onSuccess(data: List<Ticket>) {
                    view.showTickets(data)
                }

                override fun onError(e: Throwable) {
                    view.showMessage()
                }
            },
        )
    }
}
