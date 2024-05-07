package woowacourse.movie.ui.complete

import woowacourse.movie.model.db.UserTicketRepository

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        val userTicketRepository = UserTicketRepository.instance()
        try {
            Thread {
                val userTicket = userTicketRepository.getUserTicket(ticketId)
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
