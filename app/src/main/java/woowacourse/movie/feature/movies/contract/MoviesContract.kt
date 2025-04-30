package woowacourse.movie.feature.movies.contract

import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings
import woowacourse.movie.feature.model.MovieUiModel

interface MoviesContract {
    interface View {
        fun showMovies(movies: List<MovieUiModel>)

        fun showTheaters(screenings: Screenings)

        fun navigateToBookingDetail(screening: Screening)
    }

    interface Presenter {
        fun prepareMovies()

        fun selectMovieForBooking(movie: MovieUiModel)

        fun selectTheater(screening: Screening)
    }
}
