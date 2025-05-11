package woowacourse.movie.presentation.view.reservation.seat

import woowacourse.movie.presentation.model.ReservationInfoUiModel

interface SeatSelectContract {
    interface View {
        fun showErrorDialog()

        fun showReservationInfo(
            title: String,
            price: Int,
        )

        fun showSeatCountError(count: Int)

        fun showSelectedSeat(seatId: String)

        fun showDeselectedSeat(seatId: String)

        fun showTotalPrice(totalPrice: Int)

        fun updateConfirmButtonEnabled(isEnabled: Boolean)

        fun showReservationDialog(
            title: String,
            message: String,
        )

        fun showExactAlarmSettingDialog(reservationInfo: ReservationInfoUiModel)

        fun navigateCompleteWithAlarmCheck(reservationInfoUiModel: ReservationInfoUiModel)

        fun navigateToComplete(reservationInfo: ReservationInfoUiModel)
    }

    interface Presenter {
        fun fetchData(reservationInfo: ReservationInfoUiModel?)

        fun seatSelect(seatId: String)

        fun confirmRequested(
            title: String,
            message: String,
        )

        fun saveReservation(reservationInfo: ReservationInfoUiModel)

        fun getSelectedSeatIds(): List<String>

        fun reservationConfirmed()

        fun restoreSelectedSeats(seatIds: List<String>)

        fun restoreButtonState()
    }
}
