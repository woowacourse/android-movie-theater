package woowacourse.movie.feature.detail

import woowacourse.movie.model.Movie
import woowacourse.movie.util.BasePresenter

interface MovieDetailContract {
    interface View {
        fun displayMovieDetail(movie: Movie)

        fun updateReservationCountView(count: Int)

        fun navigateToSeatSelectionView(
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            reservationCount: Int,
        )

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadMovieDetail(movieId: Long)

        fun plusReservationCount()

        fun minusReservationCount()

        fun reserveMovie(
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
        )

        fun updateReservationCount(count: Int)
    }
}
