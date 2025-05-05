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

    override fun loadSeats(reservationInfo: ReservationInfo) {
        val seats = seatFactory.createSeats()
        this.reservationInfo = reservationInfo
        ticket = reservationInfo.toTicket(listOf())
        view.showSeats(seats)
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        runCatching {
            ticket = ticket.updateSeats(seat)
            view.updateSeatSelection(seat)
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
        when (ticket.isCompleted()) {
            true -> view.showReservationDialog()
            false -> view.showError("좌석을 선택해주세요")
        }
    }

    override fun completeReservation() {
        view.navigateToResult(ticket)
    }
}
