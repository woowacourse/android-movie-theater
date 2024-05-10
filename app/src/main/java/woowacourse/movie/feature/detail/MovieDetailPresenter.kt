package woowacourse.movie.feature.detail

import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.reservation.ReservationRepository
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

    override fun increaseReservationCount() {
        reservationCount = ++reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun decreaseReservationCount() {
        reservationCount = --reservationCount
        view.updateReservationCountView(reservationCount.count)
    }

    override fun reserveMovie(
        reservationRepository: ReservationRepository,
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        theaterPosition: Int,
    ) {
        val movie = MovieRepository.getMovieById(movieId)
        val theaterName = movie.theaters[theaterPosition].name

        val reservationId =
            reservationRepository.save(
                movieId,
                screeningDate.unFormatSpinnerLocalDate(),
                screeningTime.unFormatSpinnerLocalTime(),
                reservationCount,
                theaterName,
            )
        view.navigateToSeatSelectionView(reservationId)
    }
}
