package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.Ticket

interface SeatSelectionContract {
    interface View {
        fun showSeats(seats: List<Seat>)

        fun updateSeatSelection(seat: Seat)

        fun showTotalPrice(price: Int)

        fun enableConfirmButton(enabled: Boolean)

        fun showError(message: String)

        fun showReservationDialog()

        fun navigateToResult(ticket: Ticket)
    }

    interface Presenter {
        fun loadSeats(reservationInfo: ReservationInfo?)

        fun selectSeat(seat: Seat)

        fun showConfirmButton()

        fun completeReservation()
    }
}

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private var reservationInfo: ReservationInfo? = null
    private var ticket: Ticket? = null

    private val seatFactory = SeatFactory.default()

    override fun loadSeats(reservationInfo: ReservationInfo?) {
        val seats = seatFactory.createSeats()
        this.reservationInfo = reservationInfo

        view.showSeats(seats)
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        try {
            ticket ?: let {
                ticket = reservationInfo?.toTicket(listOf())
            }
            ticket = ticket?.updateSeats(seat)

            view.updateSeatSelection(seat)
            updateScreen()
        } catch (e: IllegalArgumentException) {
            view.showError(e.message ?: "좌석 선택 오류")
        }
    }

    private fun updateScreen() {
        view.showTotalPrice(ticket?.totalPrice() ?: 0)
        view.enableConfirmButton(canCompleteReservation())
    }

    private fun canCompleteReservation(): Boolean =
        ticket?.let {
            it.seats.size == it.reservationCount.value
        } ?: false

    override fun showConfirmButton() {
        if (canCompleteReservation()) {
            view.showReservationDialog()
        } else {
            view.showError("좌석을 선택해주세요")
        }
    }

    override fun completeReservation() {
        ticket?.let {
            view.navigateToResult(it)
        }
    }
}
