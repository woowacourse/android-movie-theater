package woowacourse.movie.view.complete

import woowacourse.movie.domain.Callback
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.model.Ticket

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val dataSource: TicketDataSource,
) : BookingCompleteContract.Presenter {
    override fun loadTicket(
        ticketId: Long,
        requestAlarm: Boolean,
    ) {
        dataSource.getTicketById(
            ticketId,
            object : Callback<Ticket> {
                override fun onSuccess(data: Ticket) {
                    view.showTicket(data)
                    if (requestAlarm) view.generateAlarm(data)
                }

                override fun onError(e: Throwable) {
                    view.showMessage()
                }
            },
        )
    }
}
