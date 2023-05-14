package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.database.BookHistories
import woowacourse.movie.database.BookingHistoryRepository

class BookHistoryPresenter(private val view: BookHistoryContract.View) :
    BookHistoryContract.Presenter {

    override fun onClickItem(position: Int) {
        view.showDetailPage(position)
    }

    override fun reloadBookingData(bookingHistory: BookingHistoryRepository) {
        if (!bookingHistory.isEmpty()) {
            BookHistories.items.clear()
            BookHistories.items.addAll(bookingHistory.getAll())
        }
    }
}
