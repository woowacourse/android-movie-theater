package woowacourse.movie.ui.history

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.domain.UserTicket
import woowacourse.movie.domain.mapper.toUserTicket
import java.lang.IllegalStateException
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val ticketDao: TicketDao,
) : BookingHistoryContract.Presenter {
    override fun loadHistoryItems() {
        var items: List<UserTicket>? = null
        thread {
            items = ticketDao.findAll().map(TicketEntity::toUserTicket)
        }.join()
        items?.let {
            view.showHistoryItems(it)
        } ?: view.showError(IllegalStateException())
    }
}
