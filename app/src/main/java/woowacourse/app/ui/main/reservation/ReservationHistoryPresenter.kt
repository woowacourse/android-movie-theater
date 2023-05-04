package woowacourse.app.ui.main.reservation

import woowacourse.app.usecase.reservation.ReservationUseCase
import woowacourse.domain.reservation.Reservation

class ReservationHistoryPresenter(override val reservationUseCase: ReservationUseCase) :
    ReservationHistoryContract.Presenter {
    override fun getReservation(id: Long): Reservation? {
        return reservationUseCase.getReservation(id)
    }

    override fun getReservations(): List<Reservation> {
        return reservationUseCase.getReservations()
    }
}
