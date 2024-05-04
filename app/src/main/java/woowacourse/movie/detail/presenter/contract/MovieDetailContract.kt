package woowacourse.movie.detail.presenter.contract

import woowacourse.movie.model.Movie

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
    }

    interface Presenter {
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
