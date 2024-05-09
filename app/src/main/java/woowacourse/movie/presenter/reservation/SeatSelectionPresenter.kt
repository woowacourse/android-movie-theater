package woowacourse.movie.presenter.reservation

import android.content.Context
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.TheaterSeat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.notification.TicketNotification
import woowacourse.movie.repository.ReservationTicketRepository

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val seatsDao: SeatsDao,
    private val screeningDao: ScreeningDao,
    private val repository: ReservationTicketRepository,
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

    override fun initSeatNumbers() {
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
        theaterSeat: TheaterSeat,
    ) {
        seats.apply {
            manageSelectedIndex(isSelected, index)
            manageSelected(isSelected, theaterSeat)
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

        saveTicket(ticket)
    }

    override fun saveTicket(ticket: Ticket) {
        val ticketId = repository.saveReservationTicket(ticket)
        view.navigateToFinished(ticket, ticketId)
    }

    override fun updateTotalPrice(
        isSelected: Boolean,
        theaterSeat: TheaterSeat,
    ) {
        val totalPrice = seats.calculateAmount()
        view.showAmount(totalPrice)
    }

    override fun settingAlarm(
        context: Context,
        movieTitle: String,
        ticket: Ticket,
        ticketId: Long,
    ) {
        TicketNotification.setNotification(
            context = context,
            movieTitle = movieTitle,
            screeningDateTime = ticket.screeningDateTime,
            ticketId = ticketId,
        )
    }

    override fun initializeConfirmButton() {
        view.launchReservationConfirmDialog()
    }
}
