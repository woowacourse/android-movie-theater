package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.data.BookedTicketsData
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.data.settings.SettingsData
import woowacourse.movie.domain.model.rules.SeatsPayment
import woowacourse.movie.domain.model.tools.Money
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.domain.model.tools.seat.Location
import woowacourse.movie.domain.model.tools.seat.Seat
import woowacourse.movie.domain.model.tools.seat.SeatRow
import woowacourse.movie.domain.model.tools.seat.Seats
import woowacourse.movie.domain.model.tools.seat.Theater
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.model.SeatModel
import java.time.LocalDateTime

class ChoiceSeatPresenter(
    private val view: ChoiceSeatContract.View,
    private val movieData: MovieData,
    private val settingsData: SettingsData,
) :
    ChoiceSeatContract.Presenter {

    override var isNotifiable: Boolean
        get() = settingsData.isAvailable
        set(value) {
            settingsData.isAvailable = value
        }

    private val seats: Seats = Seats()

    private var paymentAmount: Money = Money(INITIAL_PAYMENT_AMOUNT)

    private val theater = Theater.of(rows, columns)
    private fun getMovie(movieId: Long) = movieData.findMovieById(movieId)

    override fun reserveTicketModel(reservationModel: ReservationModel) {
        val movie = getMovie(reservationModel.movieId)
        val ticket =
            movie.reserve(
                reservationModel.cinemaName,
                reservationModel.bookedDateTime,
                TicketCount(reservationModel.count),
                seats,
            )
        val ticketModel = ticket.toPresentation()
        BookedTicketsData.tickets.add(ticketModel)

        view.confirmBookMovie(ticketModel)
    }

    override fun addSeat(index: Int, reservationModel: ReservationModel): Boolean {
        if (reservationModel.count <= seats.size) {
            return false
        }
        changeSeats(index, reservationModel, seats::addSeat)
        return true
    }

    override fun subSeat(index: Int, reservationModel: ReservationModel): Boolean {
        if (SEAT_NOTHING > seats.size) {
            return false
        }
        changeSeats(index, reservationModel, seats::removeSeat)
        return true
    }

    private fun changeSeats(
        index: Int,
        reservationModel: ReservationModel,
        addOrRemoveSeat: (Seat) -> Unit,
    ) {
        val seat = findSeat(index)
        addOrRemoveSeat(seat)
        setPaymentAmount(reservationModel.bookedDateTime)
        checkConfirmAble(reservationModel.count)
    }

    private fun setPaymentAmount(bookedDateTime: LocalDateTime) {
        paymentAmount =
            SeatsPayment(seats).getDiscountedMoneyByDateTime(bookedDateTime)
        view.setPaymentAmount(paymentAmount.value)
    }

    private fun checkConfirmAble(ticketCount: Int) {
        if (seats.size == ticketCount) {
            view.enableConfirm()
            return
        }
        view.disableConfirm()
    }

    override fun setMovieTitle(movieId: Long) {
        view.setMovieTitleView(getMovie(movieId).title)
    }

    override fun getSeatModel(index: Int): SeatModel = findSeat(index).toPresentation()

    private fun findSeat(index: Int): Seat {
        val location = Location(indexToRow(index), index % THEATER_COLUMN)
        return requireNotNull(theater.findSeat(location))
    }

    private fun indexToRow(index: Int): SeatRow {
        val rows = SeatRow.values().toList()
        return rows[index / THEATER_COLUMN]
    }

    companion object {
        private const val INITIAL_PAYMENT_AMOUNT = 0
        private const val THEATER_COLUMN = 4
        private val rows = SeatRow.values().toList()
        private val columns = List(THEATER_COLUMN) { it }
        private const val SEAT_NOTHING = 0
    }
}
