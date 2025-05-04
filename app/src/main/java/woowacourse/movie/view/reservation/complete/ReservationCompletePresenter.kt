package woowacourse.movie.view.reservation.complete

import woowacourse.movie.domain.model.ReservationInfo

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
) : ReservationCompleteContract.Presenter {
    override fun fetchData(reservationInfo: ReservationInfo?) {
        if (reservationInfo == null) {
            view.showErrorDialog()
            return
        }

        view.showReservationInfo(reservationInfo)
    }
}
