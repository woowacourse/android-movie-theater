package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings
import woowacourse.movie.domain.Theater

interface TheaterContract {
    interface Presenter {
        fun fetchData(movie: Movie)
    }

    interface View {
        fun handleInvalidTicket()

        fun showTheaterList(showings: List<Showings>)
    }
}

class TheaterPresenter(
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    override fun fetchData(movie: Movie) {
        val showings = movie.let { Theater.findTheatersShowingMovie(it.title) }
        view.showTheaterList(showings)
    }
}
