package woowacourse.movie.feature.detail

import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.feature.detail.ui.unFormatSpinnerLocalDate
import woowacourse.movie.feature.detail.ui.unFormatSpinnerLocalTime
import woowacourse.movie.model.ReservationCount

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
) : MovieDetailContract.Presenter {
    private var reservationCount: ReservationCount = ReservationCount()

    override fun loadMovieDetail(movieId: Long) {
        val movie =
            runCatching {
                MovieRepository.getMovieById(movieId)
            }.getOrElse {
                view.showToastInvalidMovieIdError(it)
                return
            }

        view.displayMovieDetail(movie)
        view.updateReservationCountView(reservationCount.count)
    }

    override fun updateReservationCount(count: Int) {
        reservationCount = reservationCount.update(count)
        view.updateReservationCountView(reservationCount.count)
    }

    override fun plusReservationCount() {
        reservationCount = ++reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun minusReservationCount() {
        reservationCount = --reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun reserveMovie(
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
    ) {
        view.navigateToSeatSelectionView(
            movieId,
            screeningDate.unFormatSpinnerLocalDate(),
            screeningTime.unFormatSpinnerLocalTime(),
            reservationCount.count
        )
    }
}
