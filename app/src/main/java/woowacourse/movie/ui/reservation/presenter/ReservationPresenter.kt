package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.ReservationTicketMachine
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter

class ReservationPresenter(
    private val view: ReservationContract.View,
) : ReservationContract.Presenter {
    private val reservationTickets: List<MovieTicketModel> by lazy { ReservationTicketMachine.tickets }
    override fun initAdapter(onClick: (MovieTicketModel) -> Unit) {
        view.reservationAdapter =
            ReservationAdapter(reservationTickets, onClick)
    }

    override fun isEmptyMovieReservation() {
        view.setTextOnEmptyState(reservationTickets.isEmpty())
    }
}
