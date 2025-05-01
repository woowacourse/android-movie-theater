package woowacourse.movie.seat

import android.widget.TextView
import woowacourse.movie.ui.model.TicketUiModel

interface SeatSelectionContract {
    interface View {
        fun showTicket(ticket: TicketUiModel)

        fun showSeatState(
            seat: TextView,
            isSelected: Boolean,
        )

        fun showToastErrorAndFinish(message: String)

        fun setButtonEnabled(enabled: Boolean)

        fun showBookingAlertDialog(ticket: TicketUiModel)
    }

    interface Presenter {
        fun initializeData(ticket: TicketUiModel)

        fun onSeatClicked(seat: TextView)

        fun onButtonClicked()
    }
}
