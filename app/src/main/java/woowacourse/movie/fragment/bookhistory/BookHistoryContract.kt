package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.fragment.bookhistory.adapter.BookHistoryRecyclerViewAdapter
import woowacourse.movie.model.BookingHistoryData

interface BookHistoryContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(bookHistoryRecyclerViewAdapter: BookHistoryRecyclerViewAdapter)
    }

    interface Presenter {
        fun setMovieRecyclerView(
            bookingHistoryData: List<BookingHistoryData>,
            onClickItem: (Int) -> Unit
        )
    }
}
