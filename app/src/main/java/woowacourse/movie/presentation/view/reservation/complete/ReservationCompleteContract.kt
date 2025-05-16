package woowacourse.movie.presentation.view.reservation.complete

import woowacourse.movie.presentation.model.ReservationInfoUiModel

interface ReservationCompleteContract {
    interface View {
        fun showErrorDialog()

        fun showReservationInfo(reservationInfoUiModel: ReservationInfoUiModel)
    }

    interface Presenter {
        fun fetchData(reservationInfoUiModel: ReservationInfoUiModel?)
    }
}
