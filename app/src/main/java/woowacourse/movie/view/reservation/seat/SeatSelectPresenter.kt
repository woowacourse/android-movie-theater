package woowacourse.movie.view.reservation.seat

import woowacourse.movie.R
import woowacourse.movie.model.reservation.MovieTicket
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
import woowacourse.movie.model.seat.grade.RowBasedSeatGradePolicy
import woowacourse.movie.model.seat.grade.SeatGradePolicy
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
) : SeatSelectContract.Presenter {
    private lateinit var movieTicket: MovieTicket
    private var selectedSeats = Seats.create()
    private var seatGradePolicy: SeatGradePolicy = RowBasedSeatGradePolicy()

    override fun fetchData(getMovieTicket: () -> MovieTicket?) {
        val result = getMovieTicket()
        if (result == null) {
            view.showErrorMessage(R.string.seat_select_error_movie_ticket_load_failed)
            return
        }

        movieTicket = result
        view.showReservationInfo(
            movieTicket.title,
            DEFAULT_PRICE,
        )
    }

    override fun seatSelect(
        row: Row,
        col: Col,
    ) {
        if (selectedSeats.size == movieTicket.count && !selectedSeats.contains(row, col)) {
            view.showSeatCountError(movieTicket.count)
            return
        }

        val isSelected = selectedSeats.click(row, col)
        if (isSelected) {
            view.showSelectedSeat(row, col)
        } else {
            view.showDeselectedSeat(row, col)
        }

        view.showTotalPrice(selectedSeats.getTotalPrice(seatGradePolicy))
        view.updateConfirmButtonEnabled(selectedSeats.size == movieTicket.count)
    }

    override fun createReservationInfo(onCreated: (ReservationInfo) -> Unit) {
        val reservationInfo =
            ReservationInfo(
                title = movieTicket.title,
                date = movieTicket.date,
                time = movieTicket.time,
                seats = selectedSeats,
                price = selectedSeats.getTotalPrice(seatGradePolicy),
                theaterName = movieTicket.theaterName,
            )
        onCreated(reservationInfo)
    }

    override fun confirmClicked(
        title: String,
        message: String,
    ) {
        view.showReservationDialog(title, message)
    }

    fun getSelectedSeats(): List<Seat> = selectedSeats.value

    fun restoreSelectedSeats(saveSeats: List<Seat>) {
        for (saveSeat in saveSeats) {
            selectedSeats.add(saveSeat.row, saveSeat.col)
            view.showSelectedSeat(saveSeat.row, saveSeat.col)
        }
        view.showTotalPrice(selectedSeats.getTotalPrice(seatGradePolicy))
        view.updateConfirmButtonEnabled(selectedSeats.size == movieTicket.count)
    }

    fun restoreButtonState() {
        val isEnabled = selectedSeats.size == movieTicket.count
        view.updateConfirmButtonEnabled(isEnabled)
    }

    companion object {
        private const val DEFAULT_PRICE = 0
    }
}
