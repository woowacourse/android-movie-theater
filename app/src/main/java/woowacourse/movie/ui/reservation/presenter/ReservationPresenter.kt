package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationTicketMachine

class ReservationPresenter(
    private val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private val reservationTickets: List<MovieTicketModel> by lazy { ReservationTicketMachine.tickets }
    override fun getReservationTickets() {
        view.reservationTicket = reservationTickets
    }

    override fun isEmptyMovieReservation() {
        view.setTextOnEmptyState(reservationTickets.isEmpty())
    }
}
