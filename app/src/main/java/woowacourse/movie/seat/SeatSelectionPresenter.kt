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
        domainTicket = domainTicket.toggleSeat(seat)

        val isSelected = domainTicket.seats.contains(seat)
        view.showSeatState(seat, isSelected)

        val selectedCount = domainTicket.seats.countSelected()
        val shouldEnableButton = selectedCount == domainTicket.headCount.value
        view.setButtonEnabled(shouldEnableButton)

        view.showTicket(domainTicket.toUiModel())
    }

    override fun onButtonClicked() {
        val ticketUiModel = domainTicket.toUiModel()
        view.showBookingAlertDialog(ticketUiModel)
    }
}
