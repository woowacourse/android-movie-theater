package woowacourse.movie.view.movie

import woowacourse.movie.R
import woowacourse.movie.domain.model.Theaters
import woowacourse.movie.view.model.AdUiModel
import woowacourse.movie.view.model.MovieListItem
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TheaterUiModel
import woowacourse.movie.view.model.TheaterUiModels
import woowacourse.movie.view.model.toDomain
import woowacourse.movie.view.model.toPresentation
import java.time.LocalDateTime

class MoviePresenter(
    val view: MovieContract.View,
    private val dummyTheaters: Theaters,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = dummyTheaters.getAllMovies()
        val items = generateMovieListWithAds(movies.map { it.toPresentation() })
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
