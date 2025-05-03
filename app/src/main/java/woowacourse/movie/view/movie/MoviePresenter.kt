package woowacourse.movie.view.movie

import woowacourse.movie.model.MovieDao
import woowacourse.movie.view.item.Movie

class MoviePresenter(
    val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = MovieDao().getShowingMovies()
        view.showMovies(movies)
    }

    override fun reservationSelected(movie: Movie) {
        view.showTheaterInfo(movie)
    }
}
