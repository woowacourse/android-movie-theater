package woowacourse.movie.ui.view.history

import woowacourse.movie.domain.ticket.Ticket

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val tickets: List<Ticket>,
) : ReservationHistoryContract.Presenter {
    override fun presentScreen() {
        view.updateScreen(tickets)
    }
}
