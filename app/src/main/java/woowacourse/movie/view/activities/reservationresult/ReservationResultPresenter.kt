package woowacourse.movie.view.activities.reservationresult

import woowacourse.movie.domain.screening.Reservation
import woowacourse.movie.repository.ReservationRepository

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
    private val reservationId: Long,
    private val reservationRepository: ReservationRepository
) :
    ReservationResultContract.Presenter {

    private val reservation: Reservation by lazy {
        reservationRepository.findById(reservationId)
    }

    override fun loadReservation() {
        view.setReservation(ReservationUIState.from(reservation))
    }
}
