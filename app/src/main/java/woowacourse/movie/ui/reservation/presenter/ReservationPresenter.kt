package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.ui.reservation.presenter.ReservationContract.View

class ReservationPresenter(
    private val view: View,
) : ReservationContract.Presenter {

    override fun isEmptyMovieReservation() {
        val isEmpty = ReservationTicketMachine.tickets.isEmpty()

        view.setTextOnEmptyState(isEmpty)
    }
}
