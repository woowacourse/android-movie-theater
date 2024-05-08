package woowacourse.movie.ui.complete

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.TicketDao
import woowacourse.movie.model.movie.TicketEntity
import woowacourse.movie.model.movie.UserTicket

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTicketDataSource: TicketDao,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        try {
            Thread {
                val userTicket = userTicketDataSource.find(ticketId)
                view.showReservationResult(userTicket)
            }.start()
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
