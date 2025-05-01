package woowacourse.movie.booking.complete

import woowacourse.movie.booking.detail.TicketUiModel

interface BookingCompleteContract {
    interface Presenter {
        fun initializeData()
    }

    interface View {
        fun showBookingCompleteResult(ticket: TicketUiModel)

        fun showToastErrorAndFinish(message: String)
    }
}
