package woowacourse.movie.feature.seatselection

import android.widget.Button
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount
import java.time.LocalDateTime

typealias OnReservationDataSave = (HeadCount, Seats, seatsIndex: List<Int>) -> Unit

interface SeatSelectionContract {
    interface Presenter {
        fun loadReservationInformation()

        fun loadSeatNumber()

        fun loadMovie()

        fun updateTotalPrice()

        fun requestReservationConfirm()

        fun restoreReservation()

        fun restoreSeats(
            selectedSeats: Seats?,
            seatsIndex: ArrayList<Int>?,
        )

        fun manageSelectedSeats(
            isSelected: Boolean,
            index: Int,
            seat: Seat,
        )

        fun saveTicket(screeningDateTime: LocalDateTime?)

        fun validateReservationAvailable()

        fun deliverReservationInfo(onReservationDataSave: OnReservationDataSave)

        fun updateReservationState(
            seat: Seat,
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

        fun Button.updateReservationInformation(seat: Seat)

        fun updateSeatSelectedState(
            index: Int,
            isSelected: Boolean,
        )

        fun setConfirmButtonEnabled(isEnabled: Boolean)

        fun showMovieTitle(movie: Movie)

        fun showAmount(amount: Int)

        fun launchReservationConfirmDialog()

        fun navigateToFinished(ticketId: Long)

        fun restoreSelectedSeats(selectedSeats: List<Int>)

        fun showErrorSnackBar()
    }
}
