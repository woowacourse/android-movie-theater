package woowacourse.movie.presentation.seatSelection

import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SeatingSystem

interface SeatSelectionContract {
    interface View {
        fun displaySeats(seats: List<Seat>)

        fun bindMovie(movie: Movie)

        fun updateSelectedSeatUI(index: Int)

        fun updateUnSelectedSeatUI(index: Int)

        fun bindSeatingSystem(seatingSystem: SeatingSystem)

        fun updateViews()

        fun showToastMessage(message: String?)

        fun navigate(reservation: Reservation)
    }

    interface Presenter {
        fun loadMovieData(id: Long)

        fun loadSeats()

        fun updateSeatSelection(index: Int)

        fun navigate(
            screeningDate: String,
            screeningTime: String,
            theaterId: Long,
        )
    }
}
