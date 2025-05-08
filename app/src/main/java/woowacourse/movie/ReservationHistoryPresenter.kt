package woowacourse.movie

import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.domain.ticket.Reservation

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistories() {
        view.updateReservationHistories()
    }

    override fun selectReservation(reservation: Reservation) {
        view.showTicket(reservation)
    }
}
