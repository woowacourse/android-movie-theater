package woowacourse.movie.ui.complete

import woowacourse.movie.model.movie.TicketDao
import kotlin.concurrent.thread

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTicketDataSource: TicketDao,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        try {
            thread {
                val userTicket = userTicketDataSource.find(ticketId)
                view.showReservationResult(userTicket)
            }
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
