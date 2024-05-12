package woowacourse.movie.feature.detail

import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
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
                MovieRepositoryImpl.getMovieById(movieId)
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

    override fun increaseReservationCount() {
        reservationCount = ++reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun decreaseReservationCount() {
        reservationCount = --reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun reserveMovie(
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        theaterPosition: Int,
    ) {
        val movie = MovieRepositoryImpl.getMovieById(movieId)
        val theaterName = movie.theaters[theaterPosition].name

        val reservationId =
            ReservationRepositoryImpl.save(
                movieId,
                screeningDate.unFormatSpinnerLocalDate(),
                screeningTime.unFormatSpinnerLocalTime(),
                reservationCount,
                theaterName,
            )
        view.navigateToSeatSelectionView(reservationId)
    }
}
