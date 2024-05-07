package woowacourse.movie.ui.complete

import woowacourse.movie.model.data.MovieDataSource
import woowacourse.movie.model.db.UserTicket

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTickets: MovieDataSource<UserTicket>,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        try {
            val userTicket = userTickets.find(ticketId)
            view.showTicket(userTicket)
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
