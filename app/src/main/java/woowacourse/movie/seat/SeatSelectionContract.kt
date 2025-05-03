package woowacourse.movie.seat

import woowacourse.movie.model.seat.Seat
import woowacourse.movie.ui.model.TicketUiModel

interface SeatSelectionContract {
    interface View {
        fun showTicket(ticket: TicketUiModel)

        fun showSeatState(
            seat: Seat,
            isSelected: Boolean,
        )

        fun showToastErrorAndFinish(message: String)

        fun setButtonEnabled(enabled: Boolean)

        fun showBookingAlertDialog(ticket: TicketUiModel)
    }

    interface Presenter {
        fun initializeData(ticket: TicketUiModel)

        fun onSeatClicked(seat: Seat)

        fun onButtonClicked()
    }
}
