package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel

interface BookingCompleteContract {
    interface View {
        fun showBookingCompleteResult(ticket: TicketUiModel)

        fun makeAlarm(
            ticket: TicketUiModel,
            time: Long,
        )
    }

    interface Presenter {
        fun initializeData(ticket: TicketUiModel)
    }
}
