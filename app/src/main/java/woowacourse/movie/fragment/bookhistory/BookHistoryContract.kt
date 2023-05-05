package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.fragment.bookhistory.adapter.BookHistoryRecyclerViewAdapter

interface BookHistoryContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(bookHistoryRecyclerViewAdapter: BookHistoryRecyclerViewAdapter)
    }

    interface Presenter {
        fun setMovieRecyclerView(onClickItem: (Int) -> Unit)
    }
}
