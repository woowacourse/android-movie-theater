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
        view.showMovieInfo(ticket.movie)
        view.showTotalPrice(ticket.totalPrice())
        view.updateConfirmButtonState(ticket.isFull())
        ticket.seats.seats.forEach {
            view.updateSeatSelectionState(it, true)
        }
    }

    override fun selectSeat(seat: Seat) {
        _ticket =
            if (ticket.seats.contains(seat)) {
                ticket.copy(seats = ticket.seats - seat)
            } else {
                ticket.copy(seats = ticket.seats + seat)
            }
        view.updateSeatSelectionState(seat, ticket.seats.contains(seat))
        view.updateConfirmButtonState(ticket.isFull())
        view.showTotalPrice(ticket.totalPrice())
    }

    override fun finishBooking() {
        ticketRepository.save(ticket)
        ticketAlarm.setTicketAlarm(ticket)
        view.navigateToSummary(ticket)
    }

    override fun restoreTicket(ticket: Ticket) {
        _ticket = ticket
        loadSeatSelect()
    }
}
