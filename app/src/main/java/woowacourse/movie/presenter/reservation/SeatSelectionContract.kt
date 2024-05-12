package woowacourse.movie.presenter.reservation

import android.content.Context
import android.widget.Button
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.SeatSelection
import woowacourse.movie.model.seats.TheaterSeat
import woowacourse.movie.model.ticket.Ticket

interface SeatSelectionContract {
    interface Presenter {
        fun initSeatNumbers()

        fun loadMovie(movieId: Int)

        fun updateTotalPrice(
            isSelected: Boolean,
            theaterSeat: TheaterSeat,
        )

        fun setTicketAlarm(
            context: Context,
            movieTitle: String,
            ticket: Ticket,
            ticketId: Long,
        )

        fun initializeConfirmButton()

        fun restoreReservation(count: Int)

        fun restoreSeats(
            selectedSeatSelection: SeatSelection,
            seatsIndex: List<Int>,
        )

        fun manageSelectedSeats(
            isSelected: Boolean,
            index: Int,
            theaterSeat: TheaterSeat,
        )

        fun makeTicket(
            movieId: Int,
            theaterId: Int,
            screeningDateTime: ScreeningDateTime,
        )

        fun saveTicket(ticket: Ticket)
    }

    interface View {
        fun initializeSeatsTable(
            index: Int,
            theaterSeat: TheaterSeat,
        )

        fun setUpSeatColorByGrade(grade: Grade): Int

        fun Button.showSeatNumber(theaterSeat: TheaterSeat)

        fun Button.updateReservationInformation(
            index: Int,
            theaterSeat: TheaterSeat,
        )

        fun updateSeatSelectedState(
            index: Int,
            isSelected: Boolean,
        )

        fun setConfirmButtonEnabled(count: Int)

        fun showMovieTitle(movie: Movie)

        fun showAmount(amount: Int)

        fun launchReservationConfirmDialog()

        fun navigateToFinished(
            ticket: Ticket,
            ticketId: Long,
        )

        fun restoreSelectedSeats(selectedSeats: List<Int>)

        fun showErrorMessage()
    }
}
