package woowacourse.movie.presenter.reservation

import woowacourse.movie.contract.reservation.ReservationHistoryContract
import woowacourse.movie.data.reservation.ReservationData
import woowacourse.movie.domain.reservation.ReservationSortingPolicy
import woowacourse.movie.domain.reservation.ShowtimeAscendingPolicy
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.view.reservation.LocalReservationData
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationData: ReservationData = LocalReservationData,
    private val reservationSortingPolicy: ReservationSortingPolicy = ShowtimeAscendingPolicy(),
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistories() {
        thread {
            val reservations: List<Reservation> = reservationData.reservations()
            val sortedReservations = reservationSortingPolicy.sort(reservations)
            view.updateReservationHistories(sortedReservations)
        }
    }

    override fun selectReservation(reservation: Reservation) {
        view.showTicket(reservation)
    }
}
