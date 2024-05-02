package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation

interface SeatReservationContract {
    interface View {
        fun initBinding(
            totalPrice: Int,
            timeReservation: TimeReservation,
        )

        fun updateTotalPrice(totalPrice: Int)

        fun showAllSeats(seats: Seats)

        fun activateReservation(boolean: Boolean)

        fun navigateToCompleteReservation(
            reservationId: Int,
            theaterId: Int,
        )

        fun showSeatReservationFail(throwable: Throwable)

        fun showDialog(
            reservationId: Int,
            theaterId: Int,
        )

        fun showToast(e: Throwable)
    }

    interface Presenter {
        fun loadData(timeReservationId: Int)

        fun selectSeat(
            position: Position,
            seatView: android.view.View,
        )

        fun attemptReservation(
            screenId: Int,
            theaterId: Int,
        )

        fun completeReservation(
            screenId: Int,
            theaterId: Int,
        )
    }
}
