package woowacourse.app.presentation.ui.main.reservation

import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository

class ReservationHistoryPresenter(override val reservationRepository: ReservationRepository) :
    ReservationHistoryContract.Presenter {
    override fun getReservation(id: Long): Reservation? {
        return reservationRepository.getReservation(id)
    }

    override fun getReservations(): List<Reservation> {
        return reservationRepository.getReservations()
    }
}
