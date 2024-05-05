package woowacourse.movie.ui.complete

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.UserTicket

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTicketDataSource: DefaultMovieDataSource<Long, UserTicket>,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        try {
            val userTicket = userTicketDataSource.find(ticketId)
            view.showReservationResult(userTicket)
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
