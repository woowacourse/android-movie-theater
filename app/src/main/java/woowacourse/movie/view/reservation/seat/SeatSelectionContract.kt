package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Ticket

interface SeatSelectionContract {
    interface View {
        fun showSeats(
            seats: List<Seat>,
            selected: List<Seat>,
        )

        fun showTotalPrice(price: Int)

        fun enableConfirmButton(enabled: Boolean)

        fun showError(message: String?)

        fun showReservationDialog()

        fun navigateToResult(ticket: Ticket)
    }

    interface Presenter {
        fun loadSeats(reservationInfo: ReservationInfo)

        fun selectSeat(seat: Seat)

        fun showConfirmButton()

        fun completeReservation()
    }
}
