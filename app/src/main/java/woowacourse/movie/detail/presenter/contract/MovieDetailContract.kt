package woowacourse.movie.detail.presenter.contract

import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.MovieReservationCount
import java.time.LocalTime

interface MovieDetailContract {
    interface View {
        fun updateCountView(count: Int)

        fun setUpDateSpinner(movieDate: MovieDate)

        fun setUpTimeSpinner(screeningTimes: List<LocalTime>)

        fun displayMovieDetail(
            movieData: Movie?,
            movieReservationCount: MovieReservationCount,
        )

        fun navigateToSeatSelectionView(
            movieId: Long,
            date: String,
            time: String,
            count: Int,
        )

        fun setUpTimeSpinnerPosition(position: Int)
    }

    interface Presenter {
        fun loadMovieDetail(
            movieId: Long,
            theaterPosition: Int,
        )

        fun plusReservationCount()

        fun minusReservationCount()

        fun reserveMovie(
            movieId: Long,
            date: String,
            time: String,
        )

        fun updateReservationCount(count: Int)

        fun updateTimeSpinnerPosition(position: Int)
    }
}
