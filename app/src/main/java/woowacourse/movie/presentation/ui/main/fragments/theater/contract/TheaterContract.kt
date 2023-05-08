package woowacourse.movie.presentation.ui.main.fragments.theater.contract

import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.Theater

interface TheaterContract {
    interface View {
        val presenter: Presenter

        fun showTheaterList(items: List<Theater>)
        fun showTicketingScreen(movie: Movie, theater: Theater)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun loadTheaterList()
        abstract fun handleItem(item: Theater)
    }
}
