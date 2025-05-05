package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.theatersDummy

class TheaterPresenter(
    private val view: TheaterContract.View,
    private val theaters: List<Theater> = theatersDummy,
) : TheaterContract.Presenter {
    override fun fetchData(movie: Movie) {
        val showings = movie.let { Theater.findTheatersShowingMovie(it.title, theaters) }
        view.showTheaterList(showings)
    }
}
