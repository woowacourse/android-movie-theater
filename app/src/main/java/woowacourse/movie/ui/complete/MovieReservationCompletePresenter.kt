package woowacourse.movie.ui.complete

import woowacourse.movie.model.db.UserTicketDatabase

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        val db = UserTicketDatabase.database()
        try {
            Thread {
                val userTicket = db.userTicketDao().find(ticketId)
                view.showTicket(userTicket)
            }.start()
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
