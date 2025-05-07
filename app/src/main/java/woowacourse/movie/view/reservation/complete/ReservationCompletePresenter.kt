package woowacourse.movie.view.reservation.complete

import woowacourse.movie.R
import woowacourse.movie.model.ReservationInfo

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
) : ReservationCompleteContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo

    override fun fetchData(getReservationInfo: () -> ReservationInfo?) {
        val result = getReservationInfo()
        if (result == null) {
            view.showErrorMessage(R.string.reservation_complete_error_reservation_info_load_failed)
            view.finishView()
            return
        }
        reservationInfo = result
        view.showReservationInfo(reservationInfo)
    }
}
