package woowacourse.movie.ui.booking

import woowacourse.movie.model.db.UserTicket

interface MovieBookingHistoryContract {
    interface View {
        fun showBookingHistories(bookingHistories: List<UserTicket>)
    }

    interface Presenter {
        fun loadBookingHistories()
    }
}
