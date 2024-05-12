package woowacourse.movie.feature.detail

import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.util.BasePresenter

interface MovieDetailContract {
    interface View {
        fun displayMovieDetail(movie: Movie)

        fun updateReservationCountView(count: Int)

        fun navigateToSeatSelectionView(reservationId: Long)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadMovieDetail(movieId: Long)

        fun increaseReservationCount()

        fun decreaseReservationCount()

        fun reserveMovie(
            reservationRepository: ReservationRepository,
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            theaterPosition: Int,
        )

        fun updateReservationCount(count: Int)
    }
}
