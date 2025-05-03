package woowacourse.movie.view.movie

import woowacourse.movie.R
import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.domain.model.toUiModel
import woowacourse.movie.view.model.AdUiModel
import woowacourse.movie.view.model.MovieListItem
import woowacourse.movie.view.model.MovieUiModel

class MoviePresenter(
    val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = MovieDao().getShowingMovies().map { it.toUiModel() }
        val items = generateMovieListWithAds(movies)
        view.showMovies(items)
    }

    override fun reservationSelected(movie: MovieUiModel) {
        view.showTheaterInfo(movie)
    }

    private fun generateMovieListWithAds(movies: List<MovieUiModel>): List<MovieListItem> =
        movies.chunked(MOVIE_COUNT).flatMap { chunk ->
            chunk + AdUiModel(AD_NAME, R.drawable.advertisement)
        }

    companion object {
        private const val MOVIE_COUNT = 3
        private const val AD_NAME = "광고"
    }
}
