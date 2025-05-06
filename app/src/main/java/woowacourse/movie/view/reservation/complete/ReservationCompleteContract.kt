package woowacourse.movie.view.reservation.complete

import woowacourse.movie.view.model.ReservationInfoUiModel

interface ReservationCompleteContract {
    interface View {
        fun showErrorDialog()

        fun showReservationInfo(reservationInfoUiModel: ReservationInfoUiModel)
    }

    interface Presenter {
        fun fetchData(reservationInfoUiModel: ReservationInfoUiModel?)
    }
}
