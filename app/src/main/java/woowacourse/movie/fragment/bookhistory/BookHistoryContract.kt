package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.database.BookingHistoryRepository

interface BookHistoryContract {
    interface View {
        var presenter: Presenter

        fun showDetailPage(dataPosition: Int)
    }

    interface Presenter {
        fun onClickItem(position: Int)
        fun reloadBookingData(bookingHistory: BookingHistoryRepository)
    }
}
