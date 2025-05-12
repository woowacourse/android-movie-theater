package woowacourse.movie.view.main.home

import woowacourse.movie.model.database.MovieDao

class MoviePresenter(
    val view: MovieContract.View,
    val movieDao: MovieDao,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = movieDao.getShowingMovies()
        view.showMovies(movies)
    }
}
