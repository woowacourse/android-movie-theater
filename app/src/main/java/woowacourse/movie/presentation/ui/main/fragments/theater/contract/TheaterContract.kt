package woowacourse.movie.presentation.ui.main.fragments.theater.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.Theater

interface TheaterContract {
    interface View : BaseContract.View {
        fun showTheaterList(items: List<Theater>)
        fun showTicketingScreen(movie: Movie, theater: Theater)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun loadTheaterList()
        abstract fun handleItem(item: Theater)
    }
}
