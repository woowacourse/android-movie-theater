package woowacourse.movie.view.reservation.seat

import androidx.annotation.StringRes
import woowacourse.movie.model.reservation.MovieTicket
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row

interface SeatSelectContract {
    interface View {
        fun showErrorMessage(
            @StringRes messageResId: Int,
        )

        fun showReservationInfo(
            title: String,
            price: Int,
        )

        fun showSeatCountError(count: Int)

        fun showSelectedSeat(
            row: Row,
            col: Col,
        )

        fun showDeselectedSeat(
            row: Row,
            col: Col,
        )

        fun showTotalPrice(totalPrice: Int)

        fun updateConfirmButtonEnabled(isEnabled: Boolean)

        fun showReservationDialog(
            title: String,
            message: String,
        )

        fun finishView()

        fun setMovieAlarm(reservationInfo: ReservationInfo)

        fun navigateToComplete(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun fetchData(getMovieTicket: () -> MovieTicket?)

        fun seatSelect(
            row: Row,
            col: Col,
        )

        fun confirmReservation()
    }
}
