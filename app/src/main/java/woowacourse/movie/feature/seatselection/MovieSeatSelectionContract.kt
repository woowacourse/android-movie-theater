package woowacourse.movie.feature.seatselection

import woowacourse.movie.data.TicketRepository
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
