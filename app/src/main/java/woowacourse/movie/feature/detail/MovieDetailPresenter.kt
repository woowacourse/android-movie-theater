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
        runCatching { MovieRepositoryImpl.find(movieId) }
            .onSuccess {
                view.displayMovieDetail(it)
                view.updateReservationCountView(reservationCount.count)
            }
            .onFailure {
                view.showToastInvalidMovieIdError(it)
            }
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
        try {
            val reservationId = saveReservation(movieId, screeningDate, screeningTime, theaterPosition)
            view.navigateToSeatSelectionView(reservationId)
        } catch (exception: Exception) {
            view.showToastInvalidMovieIdError(exception)
        }
    }

    private fun saveReservation(
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        theaterPosition: Int,
    ): Long {
        val movie = MovieRepositoryImpl.find(movieId)
        val theaterName = movie.theaters[theaterPosition].name

        return ReservationRepositoryImpl.save(
            movieId,
            screeningDate.unFormatSpinnerLocalDate(),
            screeningTime.unFormatSpinnerLocalTime(),
            reservationCount,
            theaterName,
        )
    }
}
