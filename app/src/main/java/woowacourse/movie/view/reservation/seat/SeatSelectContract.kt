package woowacourse.movie.view.reservation.seat

import woowacourse.movie.view.model.ReservationInfoUiModel

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

        fun navigateToComplete(reservationInfoUiModel: ReservationInfoUiModel)
    }

    interface Presenter {
        fun fetchData(reservationInfo: ReservationInfoUiModel?)

        fun seatSelect(seatId: String)

        fun confirmRequested(
            title: String,
            message: String,
        )

        fun reservationConfirmed()
    }
}
