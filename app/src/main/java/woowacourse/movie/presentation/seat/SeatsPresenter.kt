package woowacourse.movie.presentation.seat

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat

class SeatsPresenter(
    private val view: SeatSelectContract.View,
    ticket: Ticket,
) : SeatSelectContract.Presenter {
    private var _ticket: Ticket = ticket.copy()
    val ticket: Ticket get() = _ticket

    override fun loadSeatSelect() {
        view.showMovieInfo(_ticket.movie)
        view.showTotalPrice(_ticket.totalPrice())
        view.updateConfirmButtonState(_ticket.isFull())
        ticket.seats.seats.forEach {
            view.updateSeatSelectionState(it, true)
        }
    }

    override fun selectSeat(seat: Seat) {
        _ticket =
            if (_ticket.seats.contains(seat)) {
                _ticket.copy(seats = _ticket.seats - seat)
            } else {
                _ticket.copy(seats = _ticket.seats + seat)
            }
        view.updateSeatSelectionState(seat, _ticket.seats.contains(seat))
        view.updateConfirmButtonState(_ticket.isFull())
        view.showTotalPrice(_ticket.totalPrice())
    }

    override fun finishBooking() {
        view.navigateToSummary(_ticket)
    }

    override fun restoreTicket(ticket: Ticket) {
        _ticket = ticket
        loadSeatSelect()
    }
}
