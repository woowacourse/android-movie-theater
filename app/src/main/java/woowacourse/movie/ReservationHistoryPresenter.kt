package woowacourse.movie

import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.data.reservation.ReservationData
import woowacourse.movie.domain.ticket.Reservation
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationData: ReservationData,
    private val runOnUiThread: (Runnable) -> Unit,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistories() {
        thread {
            val reservations = reservationData.reservations()
            runOnUiThread {
                view.updateReservationHistories(reservations)
            }
        }
    }

    override fun selectReservation(reservation: Reservation) {
        view.showTicket(reservation)
    }
}
