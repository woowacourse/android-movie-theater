package woowacourse.movie.feature.seatselection

import android.widget.Button
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket

interface SeatSelectionContract {
    interface Presenter {
        fun loadSeatNumber()

        fun loadMovie()

        fun updateTotalPrice(
            isSelected: Boolean,
            seat: Seat,
        )

        fun requestReservationConfirm()

        fun restoreReservation(count: Int)

        fun restoreSeats(
            selectedSeats: Seats,
            seatsIndex: List<Int>,
        )

        fun manageSelectedSeats(
            isSelected: Boolean,
            index: Int,
            seat: Seat,
        )

        fun makeTicket(screeningDateTime: ScreeningDateTime)
    }

    interface View {
        fun initializeSeatsTable(
            index: Int,
            seat: Seat,
        )

        fun setUpSeatColorByGrade(grade: Grade): Int

        fun Button.showSeatNumber(seat: Seat)

        fun Button.updateReservationInformation(
            index: Int,
            seat: Seat,
        )

        fun updateSeatSelectedState(
            index: Int,
            isSelected: Boolean,
        )

        fun setConfirmButtonEnabled(isEnabled: Boolean)

        fun showMovieTitle(movie: Movie)

        fun showAmount(amount: Int)

        fun launchReservationConfirmDialog()

        fun navigateToFinished(ticket: Ticket)

        fun restoreSelectedSeats(selectedSeats: List<Int>)

        fun showErrorToast()
    }
}
