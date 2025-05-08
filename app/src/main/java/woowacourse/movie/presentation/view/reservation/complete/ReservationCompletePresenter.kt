package woowacourse.movie.presentation.view.reservation.complete

import woowacourse.movie.presentation.model.ReservationInfoUiModel

class ReservationCompletePresenter(
    val view: ReservationCompleteContract.View,
) : ReservationCompleteContract.Presenter {
    override fun fetchData(reservationInfoUiModel: ReservationInfoUiModel?) {
        if (reservationInfoUiModel == null) {
            view.showErrorDialog()
            return
        }

        view.showReservationInfo(reservationInfoUiModel)
    }
}
