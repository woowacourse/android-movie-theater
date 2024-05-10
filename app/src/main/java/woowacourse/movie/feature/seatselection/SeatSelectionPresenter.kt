package woowacourse.movie.feature.seatselection

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.theater.Theater.Companion.DEFAULT_THEATER_ID
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.HeadCount.Companion.DEFAULT_HEAD_COUNT
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    seatsDao: SeatsDao,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
    private val theaterId: Int,
    private val headCount: HeadCount,
    private val screeningDateTime: ScreeningDateTime,
    private val ticketDao: TicketDao,
) : SeatSelectionContract.Presenter {
    private val selectedSeats = Seats()
    private val seats = seatsDao.findAll()

    override fun loadReservationInformation() {
        if (movieId != DEFAULT_MOVIE_ID &&
            theaterId != DEFAULT_THEATER_ID &&
            headCount.count != DEFAULT_HEAD_COUNT &&
            screeningDateTime != ScreeningDateTime(LocalDate.now(), LocalTime.now())
        ) {
            loadSeatNumber()
            loadMovie()
        } else {
            handleUndeliveredData()
        }
    }

    override fun restoreReservation() {
        validateReservationAvailable()
        view.showAmount(selectedSeats.calculateAmount())
    }

    override fun restoreSeats(
        seats: Seats,
        seatsIndex: List<Int>,
    ) {
        selectedSeats.restoreSeats(seats)
        selectedSeats.restoreSeatsIndex(seatsIndex)
        view.restoreSelectedSeats(seatsIndex)
    }

    override fun loadSeatNumber() {
        seats.forEachIndexed { index, seat ->
            view.initializeSeatsTable(index, seat)
        }
    }

    override fun loadMovie() {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun saveTicket(screeningDateTime: ScreeningDateTime) {
        var ticketId: Long = 0
        val ticket = makeTicket(screeningDateTime)
        thread(start = true) {
            ticketId = ticketDao.insert(ticket)
        }.join()
        view.navigateToFinished(ticketId)
    }

    override fun requestReservationConfirm() {
        view.launchReservationConfirmDialog()
    }

    override fun deliverReservationInfo(onReservationDataSave: OnReservationDataSave) {
        onReservationDataSave(headCount, selectedSeats, selectedSeats.seatsIndex)
    }

    override fun updateReservationState(
        seat: Seat,
        isSelected: Boolean,
    ) {
        if (selectedSeats.seats.size < headCount.count || isSelected) {
            val seatIndex = seats.indexOf(seat)
            view.updateSeatSelectedState(seatIndex, isSelected)
            manageSelectedSeats(!isSelected, seatIndex, seat)
            updateTotalPrice()
            validateReservationAvailable()
        }
    }

    override fun manageSelectedSeats(
        isSelected: Boolean,
        index: Int,
        seat: Seat,
    ) {
        selectedSeats.apply {
            manageSelectedIndex(isSelected, index)
            manageSelected(isSelected, seat)
        }
    }

    override fun updateTotalPrice() {
        val totalPrice = selectedSeats.calculateAmount()
        view.showAmount(totalPrice)
    }

    override fun validateReservationAvailable() {
        val isReservationAvailable = selectedSeats.seats.size >= headCount.count
        view.setConfirmButtonEnabled(isReservationAvailable)
    }

    private fun handleUndeliveredData() {
        view.showErrorSnackBar()
    }

    private fun makeTicket(screeningDateTime: ScreeningDateTime): Ticket {
        val movieTitle = screeningDao.find(movieId).title
        val theaterName = theaterDao.find(theaterId).name
        val dateTime: LocalDateTime = LocalDateTime.of(screeningDateTime.date, screeningDateTime.time)

        return Ticket(
            dateTime,
            movieTitle,
            theaterName,
            selectedSeats,
        )
    }
}
