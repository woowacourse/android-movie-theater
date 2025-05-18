package woowacourse.movie.ui.view.history

import woowacourse.movie.domain.datasource.TicketDataSource
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val ticketDataSource: TicketDataSource,
) : ReservationHistoryContract.Presenter {
    override fun presentScreen() {
        thread { view.updateScreen(ticketDataSource.getAll()) }
    }
}
