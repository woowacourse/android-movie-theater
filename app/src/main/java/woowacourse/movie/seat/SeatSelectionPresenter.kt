package woowacourse.movie.seat

import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.model.seat.Col
import woowacourse.movie.model.seat.Row
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
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
        toggleSeat(seat)
        updateUI()
    }

    override fun onButtonClicked() {
        view.showBookingAlertDialog(domainTicket.toUiModel())
    }

    override fun getCurrentTicketUiModel(): TicketUiModel {
        return domainTicket.toUiModel()
    }

    override fun restoreTicketData(seats: String) {
        val restoredSeats = parseSeats(seats)
        domainTicket = domainTicket.copy(seats = Seats(restoredSeats))

        view.showTicket(domainTicket.toUiModel())
        restoredSeats.forEach { view.showSeatState(it, true) }

        updateButtonState()
    }

    private fun toggleSeat(seat: Seat) {
        domainTicket = domainTicket.toggleSeat(seat)
        val isSelected = domainTicket.seats.contains(seat)
        view.showSeatState(seat, isSelected)
    }

    private fun updateUI() {
        updateButtonState()
        view.showTicket(domainTicket.toUiModel())
    }

    private fun updateButtonState() {
        val selectedCount = domainTicket.seats.countSelected()
        val shouldEnable = selectedCount == domainTicket.headCount.value
        view.setButtonEnabled(shouldEnable)
    }

    private fun parseSeats(seats: String): List<Seat> {
        return seats
            .split(", ")
            .filter { it.isNotBlank() }
            .mapNotNull { seatName ->
                val row = seatName.firstOrNull()?.minus('A') ?: return@mapNotNull null
                val col = seatName.drop(1).toIntOrNull()?.minus(1) ?: return@mapNotNull null
                Seat(Row(row), Col(col), isSelected = true)
            }
    }
}
