package woowacourse.movie.fragment.bookhistory

interface BookHistoryContract {

    interface View {
        var presenter: Presenter
        fun setMovieRecyclerView(bookHistoryRecyclerViewAdapter: BookHistoryRecyclerViewAdapter)
    }

    interface Presenter {
        fun setMovieRecyclerView(onClickItem: (Int) -> Unit)
    }
}
