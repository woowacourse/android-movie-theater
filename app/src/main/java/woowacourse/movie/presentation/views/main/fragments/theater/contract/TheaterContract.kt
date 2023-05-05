package woowacourse.movie.presentation.views.main.fragments.theater.contract

import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.model.theater.Theater

interface TheaterContract {
    interface View {
        val presenter: Presenter

        fun showTheaterList(items: List<Theater>)
        fun showTicketingScreen(movie: Movie, theater: PresentationTheater)
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        fun detach() {
            this.view = null
        }

        fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun loadTheaterList()
        abstract fun onTheaterClick(item: Theater)
    }
}
