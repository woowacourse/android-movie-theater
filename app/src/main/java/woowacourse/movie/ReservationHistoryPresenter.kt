package woowacourse.movie

import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.domain.ticket.Ticket

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistories() {
        view.updateReservationHistories()
    }

    override fun selectReservation(ticket: Ticket) {
        view.showTicket(ticket)
    }
}
