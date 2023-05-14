package woowacourse.app.presentation.ui.main.reservation

import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository

interface ReservationHistoryContract {
    interface View {
        val presenter: Presenter

        fun initAdapter(reservations: List<Reservation>)
        fun goCompletedActivity(reservation: Reservation)
        fun showNoReservationError()
    }

    interface Presenter {
        val reservationRepository: ReservationRepository
        fun fetchReservation(id: Long)
        fun fetchReservations()
    }
}
