package woowacourse.movie.feature.seat

import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.data.reservation.dto.Reservation
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BasePresenter

interface MovieSeatSelectionContract {
    interface View {
        fun setUpReservation(
            reservation: Reservation,
            movie: Movie,
        )

        fun setUpTableSeats(baseSeats: List<MovieSeat>)

        fun updateSeatBackgroundColor(
            index: Int,
            isSelected: Boolean,
        )

        fun displayDialog()

        fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats)

        fun navigateToResultView(ticketId: Long)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadReservation(reservationId: Long)

        fun loadTableSeats(movieSelectedSeats: MovieSelectedSeats)

        fun selectSeat(index: Int)

        fun reserveMovie(
            reservation: Reservation,
            selectedSeats: MovieSelectedSeats,
        )

        fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats)
    }
}
