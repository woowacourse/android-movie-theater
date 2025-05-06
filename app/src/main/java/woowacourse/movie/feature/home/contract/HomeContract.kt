package woowacourse.movie.feature.home.contract

import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel

interface HomeContract {
    interface View {
        fun showMovies(movies: List<MovieUiModel>)

        fun showTheaters(screenings: List<ScreeningUiModel>)

        fun navigateToBookingDetail(screening: ScreeningUiModel)
    }

    interface Presenter {
        fun prepareMovies()

        fun selectMovieForBooking(movie: MovieUiModel)
    }
}
