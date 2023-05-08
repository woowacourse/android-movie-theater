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

    abstract class Presenter(protected var view: View) {
        abstract fun loadTheaterList()
        abstract fun onTheaterClick(item: Theater)
    }
}
