package woowacourse.movie.seat

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private lateinit var ticket: Ticket

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket.toDomain()
        view.showTicket(ticket)
    }

    override fun onSeatClicked(
        row: Int,
        col: Int,
    ) {
        val seat = Seat(row, col)

        if (ticket.hasSeat(seat)) {
            ticket.unselectSeat(seat)
        } else {
            ticket.selectSeat(seat)
        }

        view.showSeatState(seat.toUiModel())

        val shouldEnableButton = ticket.canReserve()
        view.setButtonEnabled(shouldEnableButton)

        view.showTicket(ticket.toUiModel())
    }

    override fun onButtonClicked() {
        val ticketUiModel = ticket.toUiModel()
        view.showBookingAlertDialog(ticketUiModel)
    }
}
