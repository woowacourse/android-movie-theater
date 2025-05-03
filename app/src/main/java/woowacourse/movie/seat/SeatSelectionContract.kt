package woowacourse.movie.seat

import woowacourse.movie.ui.model.SeatUiModel
import woowacourse.movie.ui.model.TicketUiModel

interface SeatSelectionContract {
    interface View {
        fun showTicket(ticket: TicketUiModel)

        fun showSeatState(seat: SeatUiModel)

        fun setButtonEnabled(shouldEnabled: Boolean)

        fun showBookingAlertDialog(ticket: TicketUiModel)
    }

    interface Presenter {
        fun initializeData(ticket: TicketUiModel)

        fun onSeatClicked(
            row: Int,
            col: Int,
        )

        fun onButtonClicked()
    }
}
