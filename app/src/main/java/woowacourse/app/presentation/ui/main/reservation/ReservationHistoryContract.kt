package woowacourse.app.presentation.ui.main.reservation

import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository

interface ReservationHistoryContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val reservationRepository: ReservationRepository
        fun getReservation(id: Long): Reservation?
        fun getReservations(): List<Reservation>
    }
}
