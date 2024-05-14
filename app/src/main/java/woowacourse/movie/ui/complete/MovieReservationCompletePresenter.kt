package woowacourse.movie.ui.complete

import woowacourse.movie.model.db.UserTicketRepository

class MovieReservationCompletePresenter(
    private val view: MovieReservationCompleteContract.View,
    private val userTicketRepository: UserTicketRepository,
) :
    MovieReservationCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        Thread {
            try {
                val userTicket = userTicketRepository.find(ticketId)
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
