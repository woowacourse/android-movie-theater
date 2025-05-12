package woowacourse.movie.presentation.seat

import android.content.Context
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.repository.LocalTicketRepository
import woowacourse.movie.data.repository.TicketRepository
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.presentation.notification.ticket.TicketAlarm

class SeatSelectPresenter(
    private val view: SeatSelectContract.View,
    ticket: Ticket,
    context: Context,
    private val ticketRepository: TicketRepository =
        LocalTicketRepository(MovieDatabase.getDatabase(context)),
    private val ticketAlarm: TicketAlarm = TicketAlarm(context),
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
        ticketRepository.save(_ticket)
        ticketAlarm.setTicketAlarm(_ticket)
        view.navigateToSummary(_ticket)
    }

    override fun restoreTicket(ticket: Ticket) {
        _ticket = ticket
        loadSeatSelect()
    }
}
