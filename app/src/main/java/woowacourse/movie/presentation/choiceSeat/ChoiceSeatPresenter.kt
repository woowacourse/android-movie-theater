package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.data.BookedTicketsData
import woowacourse.movie.data.MovieData
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
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.TicketModel

class ChoiceSeatPresenter(
    private val view: ChoiceSeatContract.View,
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

    private val movie = MovieData.findMovieById(view.reservation.cinemaModel.movieId)

    private val theater = Theater.of(rows, columns)

    override fun reserveTicketModel(): TicketModel {
        val reservation = view.reservation
        val ticket =
            movie.reserve(reservation.bookedDateTime, TicketCount(reservation.count), seats)
        val ticketModel = ticket.toPresentation()
        BookedTicketsData.tickets.add(ticketModel)

        return ticketModel
    }

    override fun addSeat(index: Int): Boolean {
        if (view.reservation.count <= seats.size) {
            return false
        }
        changeSeats(index, seats::addSeat)
        return true
    }

    override fun subSeat(index: Int): Boolean {
        if (SEAT_NOTHING > seats.size) {
            return false
        }
        changeSeats(index, seats::removeSeat)
        return true
    }

    private fun changeSeats(index: Int, addOrRemoveSeat: (Seat) -> Unit) {
        val seat = findSeat(index)
        addOrRemoveSeat(seat)
        setPaymentAmount()
        checkConfirmAble()
    }

    private fun setPaymentAmount() {
        paymentAmount =
            SeatsPayment(seats).getDiscountedMoneyByDateTime(view.reservation.bookedDateTime)
        view.setPaymentAmount(paymentAmount.value)
    }

    private fun checkConfirmAble() {
        if (seats.size == view.reservation.count) {
            view.enableConfirm()
            return
        }
        view.disableConfirm()
    }

    override fun getMovieModel() = movie.toPresentation()

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
