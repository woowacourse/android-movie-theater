package woowacourse.movie.presenter.seatSelection

import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.seat.Seat
import java.time.LocalDate
import java.time.LocalTime

interface SeatSelectionContracts {
    interface View {
        fun showSeats(seats: List<Seat>)

        fun showMovieTitle(title: String)

        fun showPrice(price: Int)

        fun showButtonEnabled(enabled: Boolean)

        fun updateSeatsEnabled(enabled: Boolean)

        fun showReservationCompleteView(reservationId: Long)

        fun postAlarm(
            reservationId: Long,
            movieTitle: String,
            movieDate: LocalDate,
            movieTime: LocalTime,
        )
    }

    interface Presenter {
        fun loadSeats(
            row: Int,
            column: Int,
        )

        fun updateMovieToReserve(movieToReserve: MovieToReserve)

        fun updateSelectedSeat(seat: Seat)

        fun updateMovieTicket()
    }
}
