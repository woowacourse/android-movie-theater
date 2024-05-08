package woowacourse.movie.ui.complete

import woowacourse.movie.model.db.UserTicketDatabase

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        val db = UserTicketDatabase.database()

        Thread {
            try {
                val userTicket = db.userTicketDao().find(ticketId)
                view.showTicket(userTicket)
            } catch (e: NoSuchElementException) {
                view.showError(e)
            }
        }.start()
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
