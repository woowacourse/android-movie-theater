package woowacourse.movie.presenter.seatSelection

import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.toEntity
import woowacourse.movie.model.movie.MovieToReserve
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.ticket.MovieTicket
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContracts.View,
    private val database: AppDatabase,
) : SeatSelectionContracts.Presenter {
    private lateinit var movieToReserve: MovieToReserve
    private var seats: MutableList<Seat> = mutableListOf()

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

        view.showMovieTitle(movieToReserve.movie.title)
        view.showPrice(0)
        view.showButtonEnabled(false)
    }

    override fun updateSelectedSeat(seat: Seat) {
        if (seats.contains(seat)) {
            seats.remove(seat)
        } else {
            seats.add(seat)
        }

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

    override fun updateMovieTicket() {
        val movieTicket =
            MovieTicket(
                movie = movieToReserve.movie,
                movieDate = movieToReserve.movieDate.value,
                movieTime = movieToReserve.movieTime,
                seats = seats.toList(),
                theater = movieToReserve.theater,
            )
        thread {
            val reservationId: Long =
                database.reservationDao().insertMovieTicketEntity(movieTicket.toEntity())
            view.postAlarm(
                reservationId,
                movieTicket.movie.title,
                movieTicket.movieDate,
                movieTicket.movieTime.value,
            )
            view.showReservationCompleteView(reservationId)
        }
    }
}
