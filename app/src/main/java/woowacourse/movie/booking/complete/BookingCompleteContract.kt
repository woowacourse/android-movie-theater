package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel

interface BookingCompleteContract {
    interface View {
        fun showBookingCompleteResult(ticket: TicketUiModel)

        fun showToastErrorAndFinish(message: String)
    }

    interface Presenter {
        fun initializeData(ticket: TicketUiModel)
    }
}
