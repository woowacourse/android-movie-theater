package woowacourse.movie.feature.movies.presenter

import woowacourse.movie.domain.model.Movie.Companion.movies
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings.Companion.screenings
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
        view.showTheaters(screenings.getMovieScreenings(movie.title))
    }

    override fun selectTheater(screening: Screening) {
        view.navigateToBookingDetail(screening)
    }
}
