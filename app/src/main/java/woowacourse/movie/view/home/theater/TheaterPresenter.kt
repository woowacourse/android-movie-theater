package woowacourse.movie.view.home.theater

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.toMovieUi

class TheaterPresenter(
    private val view: TheaterContract.View,
) : TheaterContract.Presenter {
    override fun fetchData(movie: Movie) {
        val movieUi: MovieUi = movie.toMovieUi()
        val showings = Theater.findTheatersShowingMovie(movieUi.title)
        view.showTheaterList(showings, movieUi)
    }
}
