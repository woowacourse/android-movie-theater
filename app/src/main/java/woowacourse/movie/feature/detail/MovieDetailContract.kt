package woowacourse.movie.feature.detail

import woowacourse.movie.model.Movie
import woowacourse.movie.util.InvalidMovieIdErrorListener

interface MovieDetailContract {
    interface View : InvalidMovieIdErrorListener {
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
