package woowacourse.movie.fragment.bookhistory

import woowacourse.movie.BookHistories
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookHistoryPresenter(private val view: BookHistoryContract.View) :
    BookHistoryContract.Presenter {
    override fun getData(): List<MovieBookingSeatInfo> = BookHistories.items

    override fun onClickItem(position: Int) {
        view.showDetailPage(position)
    }
}
