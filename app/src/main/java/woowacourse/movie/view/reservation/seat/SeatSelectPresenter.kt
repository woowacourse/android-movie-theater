package woowacourse.movie.view.reservation.seat

import woowacourse.movie.R
import woowacourse.movie.model.MovieTicket
import woowacourse.movie.model.ReservationInfo
import woowacourse.movie.model.Seats

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
) : SeatSelectContract.Presenter {
    private lateinit var movieTicket: MovieTicket
    private var selectedSeats = Seats.create()

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

    override fun seatSelect(seatId: String) {
        if (selectedSeats.size == movieTicket.count && !selectedSeats.contains(seatId)) {
            view.showSeatCountError(movieTicket.count)
            return
        }

        val isSelected = selectedSeats.click(seatId)
        if (isSelected) {
            view.showSelectedSeat(seatId)
        } else {
            view.showDeselectedSeat(seatId)
        }

        view.showTotalPrice(selectedSeats.totalPrice)
        view.updateConfirmButtonEnabled(selectedSeats.size == movieTicket.count)
    }

    override fun createReservationInfo(onCreated: (ReservationInfo) -> Unit) {
        val reservationInfo =
            ReservationInfo(
                title = movieTicket.title,
                date = movieTicket.date,
                time = movieTicket.time,
                seats = selectedSeats,
                price = selectedSeats.totalPrice,
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

    fun getSelectedSeatIds(): List<String> = selectedSeats.labels()

    fun restoreSelectedSeats(seatIds: List<String>) {
        for (seatId in seatIds) {
            selectedSeats.add(seatId)
            view.showSelectedSeat(seatId)
        }
        view.showTotalPrice(selectedSeats.totalPrice)
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
