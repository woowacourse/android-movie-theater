package woowacourse.movie.view.movie

import woowacourse.movie.model.MovieDao

class MoviePresenter(
    val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = MovieDao().getShowingMovies()
        view.showMovies(movies)
    }
}
