package woowacourse.movie.presentation.ui.main.fragments.theater.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

interface TheaterContract {
    interface View : BaseContract.View {
        fun showTheaterList(items: List<ListItem>)
        fun showTicketingScreen(movie: Movie, theater: ListItem)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun loadTheaterList()
        abstract fun handleItem(item: ListItem)
    }
}
