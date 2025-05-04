package woowacourse.movie.view.reservation.seat

import woowacourse.movie.domain.model.MovieTicket
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seats

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
) : SeatSelectContract.Presenter {
    private lateinit var movieTicket: MovieTicket
    private var selectedSeats = Seats.create()

    override fun fetchData(getMovieTicket: () -> MovieTicket?) {
        val result = getMovieTicket()
        if (result == null) {
            view.showErrorDialog()
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

    override fun confirmRequested(
        title: String,
        message: String,
    ) {
        view.showReservationDialog(title, message)
    }

    override fun reservationConfirmed() {
        val reservationInfo = createReservationInfo()
        view.navigateToComplete(reservationInfo)
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

    private fun createReservationInfo(): ReservationInfo =
        ReservationInfo(
            title = movieTicket.title,
            date = movieTicket.date,
            time = movieTicket.time,
            seats = selectedSeats,
            price = selectedSeats.totalPrice,
            theaterName = movieTicket.theaterName,
        )

    companion object {
        private const val DEFAULT_PRICE = 0
    }
}
