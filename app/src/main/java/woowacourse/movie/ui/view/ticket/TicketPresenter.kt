package woowacourse.movie.ui.view.ticket

import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.TicketHistory
import kotlin.concurrent.thread

class TicketPresenter(
    private val view: TicketContract.View,
    private val ticketDataSource: TicketDataSource,
    ticketId: Long,
    private val cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : TicketContract.Presenter {
    private lateinit var ticketHistory: TicketHistory

    init {
        thread {
            ticketHistory = ticketDataSource.getTicket(ticketId)
        }.join()
    }

    override fun presentTitle() {
        view.setMovieTitle(ticketHistory.title)
    }

    override fun presentShowtime() {
        view.setShowtime(ticketHistory.showtime)
    }

    override fun presentCancelDescription() {
        view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
    }

    override fun presentCount() {
        with(ticketHistory) {
            view.setCount(count, seats, cinemaName)
        }
    }

    override fun presentPrice() {
        view.setPrice(ticketHistory.price)
    }
}
