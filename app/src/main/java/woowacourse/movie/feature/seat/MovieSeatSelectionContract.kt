package woowacourse.movie.feature.seat

import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.reservation.dto.Reservation
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BasePresenter

interface MovieSeatSelectionContract {
    interface View {
        fun setUpReservation(reservation: Reservation)

        fun setUpTableSeats(baseSeats: List<MovieSeat>)

        fun updateSeatBackgroundColor(
            index: Int,
            isSelected: Boolean,
        )

        fun displayDialog()

        fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats)

        fun navigateToResultView(ticketId: Long)

        fun showToastInvalidMovieIdError(throwable: Throwable)

        fun setTicketAlarm(ticket: Ticket)
    }

    interface Presenter : BasePresenter {
        fun loadReservation(
            reservationRepository: ReservationRepository,
            reservationId: Long,
        )

        fun loadTableSeats(movieSelectedSeats: MovieSelectedSeats)

        fun selectSeat(index: Int)

        fun reserveMovie(
            ticketRepository: TicketRepository,
            reservation: Reservation,
            selectedSeats: MovieSelectedSeats,
        )

        fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats)
    }
}
