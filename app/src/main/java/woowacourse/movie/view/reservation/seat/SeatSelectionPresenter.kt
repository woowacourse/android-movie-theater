package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.Ticket

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var ticket: Ticket

    private val seatFactory = SeatFactory.default()
    private val seats = seatFactory.seats

    override fun loadSeats(reservationInfo: ReservationInfo) {
        this.reservationInfo = reservationInfo
        ticket = reservationInfo.toTicket(listOf())
        view.showSeats(seats, listOf())
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        runCatching {
            ticket = ticket.updateSeats(seat)
            view.showSeats(seats, ticket.seats)
            updateScreen()
        }.onFailure { e ->
            view.showError(e.message)
        }
    }

    private fun updateScreen() {
        view.showTotalPrice(ticket.totalPrice())
        view.enableConfirmButton(ticket.isCompleted())
    }

    override fun showConfirmButton() {
        view.showReservationDialog()
    }

    override fun completeReservation() {
        view.navigateToResult(ticket)
    }
}
