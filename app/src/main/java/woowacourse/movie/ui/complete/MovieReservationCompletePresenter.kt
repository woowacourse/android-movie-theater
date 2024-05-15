package woowacourse.movie.ui.complete

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.data.mapper.toUserTicket
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
            ticketEntity?.toUserTicket()?.let {
                view.showReservationResult(it)
            } ?: view.showError(IllegalStateException(ERROR_NULL_ENTITY))
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }

    companion object {
        private const val ERROR_NULL_ENTITY = "데이터를 찾을 수 없습니다."
    }
}
