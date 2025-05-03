package woowacourse.movie.seat

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private lateinit var domainTicket: Ticket

    override fun initializeData(ticket: TicketUiModel) {
        domainTicket = ticket.toDomain()
        view.showTicket(ticket)
    }

    override fun onSeatClicked(seat: Seat) {
        val row = seat.row
        val col = seat.col
        val domainSeat = Seat(row, col)

        domainTicket = domainTicket.toggleSeat(domainSeat)

        val isSelected = domainTicket.seats.values.any { it.row == row && it.col == col }
        view.showSeatState(seat, isSelected)

        val shouldEnableButton = domainTicket.seats.values.size == domainTicket.headCount.value
        view.setButtonEnabled(shouldEnableButton)

        view.showTicket(domainTicket.toUiModel())
    }

    override fun onButtonClicked() {
        val ticketUiModel = domainTicket.toUiModel()
        view.showBookingAlertDialog(ticketUiModel)
    }
}
