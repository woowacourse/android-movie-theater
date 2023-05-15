package woowacourse.app.presentation.ui.main.reservation

import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.util.CgvResult

class ReservationHistoryPresenter(
    override val reservationRepository: ReservationRepository,
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservation(id: Long) {
        val result: CgvResult<Reservation> = reservationRepository.getReservation(id)
        when (result) {
            is CgvResult.Success -> view.goCompletedActivity(result.data)
            is CgvResult.Failure -> view.showNoReservationError()
        }
    }

    override fun fetchReservations() {
        view.initAdapter(reservationRepository.getReservations())
    }
}
