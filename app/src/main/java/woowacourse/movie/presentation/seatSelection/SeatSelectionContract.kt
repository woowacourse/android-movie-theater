package woowacourse.movie.presentation.seatSelection

import android.content.Context
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SeatingSystem
import woowacourse.movie.model.Ticket

interface SeatSelectionContract {
    interface View {
        fun displaySeats(seats: List<Seat>)

        fun bindMovie(movie: Movie)

        fun updateSelectedSeatUI(index: Int)

        fun updateUnSelectedSeatUI(index: Int)

        fun bindSeatingSystem(seatingSystem: SeatingSystem)

        fun updateViews()

        fun showToastMessage(message: String?)

        fun navigate(
            ticket: Ticket,
            ticketId: Long,
        )
    }

    interface Presenter {
        fun loadMovieData(id: Long)

        fun loadSeats()

        fun updateSeatSelection(index: Int)

        fun setAlarm(
            context: Context,
            movieTitle: String,
            ticket: Ticket,
            ticketId: Long,
        )

        fun navigate(
            screeningDateTime: String,
            theaterId: Long,
            context: Context,
        )
    }
}
