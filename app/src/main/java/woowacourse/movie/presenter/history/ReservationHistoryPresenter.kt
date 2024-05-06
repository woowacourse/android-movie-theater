package woowacourse.movie.presenter.history

import woowacourse.movie.model.ticket.ReservationTicket

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun loadReservationTickets() {
//        view.showReservationHistory()
    }

    override fun loadReservationTicket(reservationTicket: ReservationTicket) {
        view.navigateToDetail(reservationTicket)
    }
}
