package woowacourse.movie.feature.seat

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BasePresenter

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

        fun clickTableSeat(index: Int)

        fun clickPositiveButton(
            ticketRepository: TicketRepository,
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            selectedSeats: MovieSelectedSeats,
            theaterName: String,
        )

        fun updateSelectedSeats(movieSelectedSeats: MovieSelectedSeats)
    }
}
