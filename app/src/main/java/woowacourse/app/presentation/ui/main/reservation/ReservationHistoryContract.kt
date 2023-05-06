package woowacourse.app.presentation.ui.main.reservation

import woowacourse.app.presentation.usecase.reservation.ReservationUseCase
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
