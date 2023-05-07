package woowacourse.app.presentation.usecase.reservation

import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository

class ReservationUseCase(private val reservationRepository: ReservationRepository) {

    fun getReservations(): List<Reservation> {
        return reservationRepository.getReservations()
    }

    fun getReservation(id: Long): Reservation? {
        return reservationRepository.getReservation(id)
    }
}
