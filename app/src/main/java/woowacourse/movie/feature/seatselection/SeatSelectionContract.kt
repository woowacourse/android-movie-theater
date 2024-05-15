package woowacourse.movie.feature.seatselection

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount

typealias OnReservationDataSave = (HeadCount, Seats, seatsIndex: List<Int>) -> Unit

interface SeatSelectionContract {
    interface Presenter {
        fun handleMovieLoading()

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

        fun saveTicket()

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
