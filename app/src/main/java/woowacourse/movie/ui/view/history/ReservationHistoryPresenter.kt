package woowacourse.movie.ui.view.history

import woowacourse.movie.ui.view.data.TicketDataAdapter
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val ticketDataAdapter: TicketDataAdapter,
) : ReservationHistoryContract.Presenter {
    override fun presentScreen() {
        thread { view.updateScreen(ticketDataAdapter.getAll()) }
    }
}
