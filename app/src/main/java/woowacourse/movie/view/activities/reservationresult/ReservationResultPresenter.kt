package woowacourse.movie.view.activities.reservationresult

import woowacourse.movie.domain.screening.Reservation
import woowacourse.movie.repository.ReservationRepository1

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
    private val reservationId: Long,
    private val reservationRepository1: ReservationRepository1
) :
    ReservationResultContract.Presenter {

    private lateinit var reservation: Reservation

    override fun loadReservation() {
        reservation = reservationRepository1.findById(reservationId)
        view.setReservation(ReservationUIState.from(reservation))
    }
}
