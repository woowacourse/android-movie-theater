package woowacourse.movie.ui.view.history

import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.TicketHistory

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val ticketDataSource: TicketDataSource,
) : ReservationHistoryContract.Presenter {
    init {
        ticketDataSource.getAll { tickets: List<TicketHistory> ->
            view.updateScreen(tickets)
        }
    }
}
