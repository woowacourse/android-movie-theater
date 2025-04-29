package woowacourse.movie.feature.movies.contract

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.feature.model.MovieUiModel

interface MoviesContract {
    interface View {
        fun showMovies(movies: List<MovieUiModel>)

        fun showTheaters(movie: MovieUiModel)

        fun navigateToBookingDetail(
            movie: MovieUiModel,
            theater: Theater,
        )
    }

    interface Presenter {
        fun prepareMovies()

        fun selectMovieForBooking(movie: MovieUiModel)

        fun selectTheater(
            movie: MovieUiModel,
            theater: Theater,
        )
    }
}
