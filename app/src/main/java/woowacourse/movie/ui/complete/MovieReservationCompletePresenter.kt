package woowacourse.movie.ui.complete

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.domain.mapper.toUserTicket
import java.lang.IllegalStateException
import kotlin.concurrent.thread

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTicketDataSource: TicketDao,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        try {
            var ticketEntity: TicketEntity? = null
            thread {
                ticketEntity = userTicketDataSource.find(ticketId)
            }.join()
            ticketEntity?.let {
                view.showReservationResult(it.toUserTicket())
            } ?: view.showError(IllegalStateException())
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
