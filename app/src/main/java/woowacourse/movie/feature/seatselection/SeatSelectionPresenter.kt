package woowacourse.movie.feature.seatselection

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.db.ticket.TicketDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.theater.Theater.Companion.DEFAULT_THEATER_ID
import woowacourse.movie.model.ticket.HeadCount
import woowacourse.movie.model.ticket.Reservation
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.model.ticket.Ticket.Companion.toEntity
import java.time.LocalDateTime
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    seatsDao: SeatsDao,
    private val screeningDao: ScreeningDao,
    private val theaterDao: TheaterDao,
    private val ticketDao: TicketDao,
    private val reservation: Reservation,
) : SeatSelectionContract.Presenter {
    private val selectedSeats = Seats()
    private val seats = seatsDao.findAll()
    private val movieId: Int = reservation.movieId
    private val theaterId: Int = reservation.theaterId
    private var isValidReservation: Boolean = false
    private lateinit var headCount: HeadCount
    private lateinit var screeningDateTime: LocalDateTime

    init {
        isValidReservation = isValidReservation()
    }

    override fun handleMovieLoading() {
        if (isValidReservation) {
            loadMovie()
        } else {
            notifyError()
        }
    }

    override fun restoreReservation() {
        validateReservationAvailable()
        view.showAmount(selectedSeats.calculateAmount())
    }

    override fun restoreSeats(
        selectedSeats: Seats?,
        seatsIndex: ArrayList<Int>?,
    ) {
        selectedSeats?.let {
            this.selectedSeats.restoreSeats(selectedSeats)
        }
        seatsIndex?.let {
            this.selectedSeats.restoreSeatsIndex(seatsIndex)
            view.restoreSelectedSeats(seatsIndex)
        }
    }

    override fun loadMovie() {
        val movie: Movie = screeningDao.find(movieId)
        view.showMovieTitle(movie)
    }

    override fun saveTicket() {
        var ticketId: Long = 0
        val ticket = makeTicket().toEntity()
        thread {
            ticketId = ticketDao.insert(ticket)
        }.join()
        view.navigateToFinished(ticketId)
    }

    override fun requestReservationConfirm() {
        view.launchReservationConfirmDialog()
    }

    override fun deliverReservationInfo(onReservationDataSave: OnReservationDataSave) {
        onReservationDataSave(selectedSeats, selectedSeats.seatsIndex)
    }

    override fun updateReservationState(
        seat: Seat,
        isSelected: Boolean,
    ) {
        if (isValidSelectedSeat(headCount) || isSelected) {
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

    private fun isValidReservation(): Boolean {
        if (reservation.movieId == DEFAULT_MOVIE_ID) return false
        if (reservation.theaterId == DEFAULT_THEATER_ID) return false
        if (reservation.headCount == null) return false
        if (reservation.screeningDateTime == null) return false
        headCount = reservation.headCount
        screeningDateTime = reservation.screeningDateTime
        return true
    }

    private fun notifyError() {
        view.showErrorSnackBar()
    }

    private fun isValidSelectedSeat(headCount: HeadCount): Boolean {
        return selectedSeats.seats.size < headCount.count
    }

    override fun loadSeatNumber() {
        seats.forEachIndexed { index, seat ->
            view.initializeSeatsTable(index, seat)
        }
    }

    private fun makeTicket(): Ticket {
        val movieTitle = screeningDao.find(movieId).title
        val theaterName = theaterDao.find(theaterId).name

        return Ticket(
            screeningDateTime,
            movieTitle,
            theaterName,
            selectedSeats,
        )
    }
}
