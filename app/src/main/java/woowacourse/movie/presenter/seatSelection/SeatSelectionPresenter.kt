package woowacourse.movie.presenter.seatSelection

import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.reservation.ReservationDatabase
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.ticket.MovieTicket

class SeatSelectionPresenter(
    private val view: SeatSelectionContracts.View,
    private val db: ReservationDatabase,
) : SeatSelectionContracts.Presenter {
    private lateinit var movieToReserve: MovieToReserve
    private val seats: MutableSet<Seat> = mutableSetOf()

    override fun loadSeats(
        row: Int,
        column: Int,
    ) {
        val seats: List<Seat> =
            (0 until row).flatMap { row ->
                (0 until column).map { col ->
                    Seat(row, col)
                }
            }
        view.showSeats(seats)
    }

    override fun updateMovieToReserve(movieToReserve: MovieToReserve) {
        this.movieToReserve = movieToReserve

        view.showMovieTitle(movieToReserve.title)
        view.showPrice(0)
        view.showButtonEnabled(false)
    }

    override fun updateSelectedSeat(seat: Seat) {
        if (!seats.add(seat)) seats.remove(seat)

        updateButtonEnabled()
        updateTotalPrice()
    }

    private fun updateButtonEnabled() {
        val buttonEnabled: Boolean = seats.size == movieToReserve.ticketCount.value
        view.updateSeatsEnabled(buttonEnabled.not())
        view.showButtonEnabled(buttonEnabled)
    }

    private fun updateTotalPrice() {
        val totalPrice: Int = seats.sumOf { seat -> seat.price }
        view.showPrice(totalPrice)
    }

    override fun requestReservationComplete() {
        val movieTicket =
            MovieTicket(
                title = movieToReserve.title,
                selectedDate = movieToReserve.movieDate.value,
                selectedTime = movieToReserve.movieTime,
                seats = seats.toList(),
                theater = movieToReserve.theater,
            )
        Thread {
            db.reservationDao().saveReservation(movieTicket)
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                view.showReservationCompleteView(movieTicket)
            }
        }.start()
    }

    override fun requestErrorDialogMessage() {
        view.showErrorDialogMessage()
    }
}
