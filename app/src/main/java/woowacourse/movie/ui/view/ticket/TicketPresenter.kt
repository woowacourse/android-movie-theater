package woowacourse.movie.ui.view.ticket

import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.TicketHistory

class TicketPresenter(
    view: TicketContract.View,
    ticketDataSource: TicketDataSource,
    ticketId: Long,
    cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : TicketContract.Presenter {
    init {
        ticketDataSource.getTicket(ticketId) { ticket: TicketHistory ->
            with(ticket) {
                view.setMovieTitle(title)
                view.setShowtime(showtime)
                view.setCount(count, seats, cinemaName)
                view.setPrice(price)
            }
            view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
        }
    }
}
