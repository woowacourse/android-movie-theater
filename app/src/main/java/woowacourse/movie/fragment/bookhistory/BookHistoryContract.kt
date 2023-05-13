package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.database.BookingHistoryRepository
import woowacourse.movie.movie.MovieBookingSeatInfo

interface BookHistoryContract {
    interface View {
        var presenter: Presenter

        fun showDetailPage(dataPosition: Int)
    }

    interface Presenter {
        fun getData(): List<MovieBookingSeatInfo>
        fun onClickItem(position: Int)
        fun reloadBookingData(bookingHistory: BookingHistoryRepository)
    }
}
