package woowacourse.movie.view.reservation.detail

import android.os.Bundle
import woowacourse.movie.domain.Movie
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.theater.Showing
import woowacourse.movie.view.reservation.Ticket
import java.time.LocalDateTime

interface ReservationContract {
    interface Presenter {
        fun fetchData(
            movie: Movie,
            showings: Showing,
        )

        fun onSaveState(outState: Bundle)

        fun onRestoreState(outState: Bundle)

        fun increasedCount()

        fun decreasedCount()

        fun selectedDate(position: Int)

        fun selectedTime(position: Int)

        fun createTicket(
            selectedDateTime: LocalDateTime,
            theaterName: String,
        )

        fun resetSelectedTimePosition(position: Int)
    }

    interface View {
        fun showErrorInvalidMovie()

        fun showMovieReservationScreen(movieUi: MovieUi)

        fun navigateToReservationComplete(ticket: Ticket)

        fun setReservationButton(showings: Showing)

        fun showCount(count: Int)

        fun showSpinnerData(
            movie: Movie,
            selectedDatePosition: Int,
            showings: Showing,
        )

        fun setTimeSelection(position: Int)
    }
}
