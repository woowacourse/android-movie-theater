package woowacourse.movie.ui.view.ticket

import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.Ticket
import kotlin.concurrent.thread

class TicketPresenter(
    private val view: TicketContract.View,
    private val ticketDataSource: TicketDataSource,
    ticketId: Long,
    private val cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : TicketContract.Presenter {
    private lateinit var ticket: Ticket

    init {
        thread {
            ticket = ticketDataSource.getTicket(ticketId)
        }.join()
    }

    override fun presentTitle() {
        view.setMovieTitle(ticket.title)
    }

    override fun presentShowtime() {
        view.setShowtime(ticket.showtime)
    }

    override fun presentCancelDescription() {
        view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
    }

    override fun presentCount() {
        with(ticket) {
            view.setCount(count, seats, cinemaName)
        }
    }

    override fun presentPrice() {
        view.setPrice(ticket.price)
    }
}
