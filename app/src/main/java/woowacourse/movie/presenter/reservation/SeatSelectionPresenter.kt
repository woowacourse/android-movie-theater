package woowacourse.movie.presenter.reservation

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val seatsDao: SeatsDao,
    private val screeningDao: ScreeningDao,
) : SeatSelectionContract.Presenter {
    val seats = Seats()
    private val headCount = HeadCount()

    override fun restoreReservation(count: Int) {
        headCount.restore(count)
        view.setConfirmButtonEnabled(headCount.count)
        view.showAmount(seats.calculateAmount())
    }

    override fun restoreSeats(
        selectedSeats: Seats,
        seatsIndex: List<Int>,
    ) {
        seats.restoreSeats(selectedSeats)
        seats.restoreSeatsIndex(seatsIndex)
        view.restoreSelectedSeats(seatsIndex)
    }

    override fun loadSeatNumber() {
        val seats = seatsDao.findAll()
        seats.forEachIndexed { index, seat ->
            view.initializeSeatsTable(index, seat)
        }
    }

    override fun loadMovie(movieId: Int) {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun manageSelectedSeats(
        isSelected: Boolean,
        index: Int,
        seat: Seat,
    ) {
        seats.apply {
            manageSelectedIndex(isSelected, index)
            manageSelected(isSelected, seat)
        }
    }

    override fun makeTicket(
        movieId: Int,
        theaterId: Int,
        screeningDateTime: ScreeningDateTime,
    ) {
        val ticket =
            Ticket(
                movieId,
                theaterId,
                seats,
                screeningDateTime,
                seats.calculateAmount(),
            )
        view.navigateToFinished(ticket)
    }

    override fun updateTotalPrice(
        isSelected: Boolean,
        seat: Seat,
    ) {
        val totalPrice = seats.calculateAmount()
        view.showAmount(totalPrice)
    }

    override fun initializeConfirmButton() {
        view.launchReservationConfirmDialog()
    }
}
