package woowacourse.movie.ui.history

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.domain.UserTicket
import woowacourse.movie.data.mapper.toUserTicket
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val ticketDao: TicketDao,
) : BookingHistoryContract.Presenter {
    override fun loadHistoryItems() {
        runCatching {
            lateinit var items: List<UserTicket>
            thread { items = ticketDao.findAll().map(TicketEntity::toUserTicket) }.join()
            view.showHistoryItems(items)
        }.onFailure {
            view.showError(it)
        }
    }
}
