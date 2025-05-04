package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.ReservationInfo

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

        fun navigateToComplete(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun fetchData(ticket: MovieTicket?)

        fun seatSelect(seatId: String)

        fun confirmRequested(
            title: String,
            message: String,
        )

        fun reservationConfirmed()
    }
}
