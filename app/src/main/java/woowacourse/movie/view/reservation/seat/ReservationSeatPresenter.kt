package woowacourse.movie.view.reservation.seat

import android.os.Bundle
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seat
import woowacourse.movie.domain.movieseat.Seats
import woowacourse.movie.util.getSerializableCompat

class ReservationSeatPresenter(
    val view: ReservationSeatContract.View,
) : ReservationSeatContract.Present {
    private lateinit var ticket: Ticket
    private var seats = Seats()

    override fun fetchData(ticket: Ticket) {
        this.ticket = ticket
        view.setSeatTag()
        view.setSeatInit()
        view.showMovieName(ticket.title)
        view.setSeatClickListener()
        view.setReservationButton {
            view.showReservationDialog(ticket, seats)
        }
        refreshUI()
    }

    override fun selectSeat(position: Position) {
        if (seats.selectedLimit(ticket.personnel).not()) {
            seats = seats.addSeat(Seat(position))
            view.selectSeatView(position)
            refreshUI()
        }
    }

    override fun deselectSeat(position: Position) {
        seats = seats.removeSeat(Seat(position))
        view.deselectSeatView(position)
        refreshUI()
    }

    private fun refreshUI() {
        updateMoney()
        updateReservationBtnState()
    }

    override fun onSaveState(outState: Bundle) {
        outState.putSerializable(KEY_SEATS, seats)
    }

    override fun onRestoreState(outState: Bundle) {
        outState.getSerializableCompat(KEY_SEATS, Seats::class.java)?.let {
            seats = it
            seats.selectedSeats.forEach { seat ->
                view.selectSeatView(seat.position)
            }
            refreshUI()
        }
    }

    override fun updateMoney() {
        view.showTicketMoney(seats.reservationPrice())
    }

    private fun updateReservationBtnState() {
        if (seats.canSelect(ticket.personnel)) {
            view.selectableButton()
        } else {
            view.deSelectableButton()
        }
    }

    companion object {
        const val KEY_SEATS = "seats"
    }
}
