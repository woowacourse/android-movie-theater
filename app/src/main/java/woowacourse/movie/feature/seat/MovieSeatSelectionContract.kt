package woowacourse.movie.feature.seat

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BasePresenter
import java.time.LocalDate
import java.time.LocalTime

interface MovieSeatSelectionContract {
    interface View {
        fun displayMovieTitle(movieTitle: String)

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
        fun loadMovieTitle(movieId: Long)

        fun loadTableSeats(movieSelectedSeats: MovieSelectedSeats)

        fun selectSeat(index: Int)

        fun reserveMovie(
            ticketRepository: TicketRepository,
            movieId: Long,
            screeningDate: LocalDate,
            screeningTime: LocalTime,
            selectedSeats: MovieSelectedSeats,
            theaterName: String,
        )

        fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats)
    }
}
