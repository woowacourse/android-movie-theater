package woowacourse.movie.feature.seatselection

import android.widget.Button
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.Ticket

typealias OnReservationDataSave = (HeadCount, Seats, seatsIndex: List<Int>) -> Unit

interface SeatSelectionContract {
    interface Presenter {
        fun loadSeatNumber()

        fun loadMovie()

        fun updateTotalPrice()

        fun requestReservationConfirm()

        fun restoreReservation()

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

        fun validateReservationAvailable()

        fun deliverReservationInfo(onReservationDataSave: OnReservationDataSave)

        fun handleUndeliveredData()

        fun updateReservationState(
            seat: Seat,
            index: Int,
            isSelected: Boolean,
        )
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

        fun showErrorSnackBar()
    }
}
