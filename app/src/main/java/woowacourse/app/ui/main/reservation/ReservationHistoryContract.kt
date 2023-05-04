package woowacourse.app.ui.main.reservation

import woowacourse.app.usecase.reservation.ReservationUseCase
import woowacourse.domain.reservation.Reservation

interface ReservationHistoryContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val reservationUseCase: ReservationUseCase
        fun getReservation(id: Long): Reservation?
        fun getReservations(): List<Reservation>
    }
}
