package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.fragment.bookhistory.adapter.BookHistoryRecyclerViewAdapter
import woowacourse.movie.model.BookingHistoryData

class BookHistoryPresenter(val view: BookHistoryContract.View) : BookHistoryContract.Presenter {

    override fun setMovieRecyclerView(bookingHistoryData: List<BookingHistoryData>, onClickItem: (Int) -> Unit) {
        val bookHistoryRecyclerViewAdapter = BookHistoryRecyclerViewAdapter(
            bookingHistoryData,
            onClickItem
        )
        view.setMovieRecyclerView(bookHistoryRecyclerViewAdapter)
    }
}
