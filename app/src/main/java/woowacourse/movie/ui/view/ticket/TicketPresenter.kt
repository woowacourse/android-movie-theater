package woowacourse.movie.ui.view.ticket

import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.TicketHistory
import kotlin.concurrent.thread

class TicketPresenter(
    view: TicketContract.View,
    ticketDataSource: TicketDataSource,
    ticketId: Long,
    cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : TicketContract.Presenter {
    private lateinit var ticketHistory: TicketHistory

    init {
        thread {
            ticketHistory = ticketDataSource.getTicket(ticketId)
        }.join()
        with(ticketHistory) {
            view.setMovieTitle(title)
            view.setShowtime(showtime)
            view.setCount(count, seats, cinemaName)
            view.setPrice(ticketHistory.price)
        }
        view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
    }
}
