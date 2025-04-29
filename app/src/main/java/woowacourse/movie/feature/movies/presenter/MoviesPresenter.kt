package woowacourse.movie.feature.movies.presenter

import woowacourse.movie.domain.model.Movie.Companion.movies
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.movies.contract.MoviesContract

class MoviesPresenter(
    private val view: MoviesContract.View,
) : MoviesContract.Presenter {
    override fun prepareMovies() {
        view.showMovies(movies.map { it.toUi() })
    }

    override fun selectMovieForBooking(movie: MovieUiModel) {
        view.showTheaters(movie)
    }

    override fun selectTheater(
        movie: MovieUiModel,
        theater: Theater,
    ) {
        view.navigateToBookingDetail(movie, theater)
    }
}
