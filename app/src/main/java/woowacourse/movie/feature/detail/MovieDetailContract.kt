package woowacourse.movie.feature.detail

import woowacourse.movie.model.Movie
import woowacourse.movie.util.BasePresenter

interface MovieDetailContract {
    interface View {
        fun displayMovieDetail(movie: Movie)

        fun updateCountView(count: Int)

        fun navigateToSeatSelectionView(
            id: Long,
            date: String,
            time: String,
            count: Int,
        )

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadMovieDetail(movieId: Long)

        fun plusReservationCount()

        fun minusReservationCount()

        fun reserveMovie(
            id: Long,
            date: String,
            time: String,
        )

        fun updateReservationCount(count: Int)
    }
}
