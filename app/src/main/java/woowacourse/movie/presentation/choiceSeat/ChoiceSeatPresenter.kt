package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.domain.model.rules.SeatsPayment
import woowacourse.movie.domain.model.tools.Money
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.seat.Location
import woowacourse.movie.domain.model.tools.seat.Seat
import woowacourse.movie.domain.model.tools.seat.SeatGrade
import woowacourse.movie.domain.model.tools.seat.SeatRow
import woowacourse.movie.domain.model.tools.seat.Seats
import woowacourse.movie.domain.model.tools.seat.Theater
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.model.data.storage.SettingStorage
import woowacourse.movie.presentation.model.ReservationModel

class ChoiceSeatPresenter(
    override val view: ChoiceSeatContract.View,
    private val settingStorage: SettingStorage,
    private val movieStorage: MovieStorage,
    private var paymentAmount: Money = Money(INITIAL_PAYMENT_AMOUNT),
    private val seats: Seats = Seats(),
    private val reservation: ReservationModel
) : ChoiceSeatContract.Presenter {
    init {
        updatePaymentAmount()
        updateConfirmButton()
    }

    override fun getSeats(): Seats = seats

    private val theater: Theater = Theater.of(rows, columns)

    override fun checkReservationCountFull(): Boolean = seats.size == reservation.count

    override fun addSeat(row: Int, column: Int) {
        seats.addSeat(getSeat(row, column))
        reCalculatePaymentAmount()
        updateConfirmButton()
    }

    override fun removeSeat(row: Int, column: Int) {
        seats.removeSeat(getSeat(row, column))
        reCalculatePaymentAmount()
        updateConfirmButton()
    }

    private fun getSeat(row: Int, column: Int): Seat =
        theater.findSeat(getLocation(row, column)) ?: throw IllegalStateException(
            SEAT_NON_EXIST_ERROR.format("${indexToRow(row)}$column")
        )

    private fun updateConfirmButton() {
        view.updateConfirmButtonState(checkReservationCountFull())
    }

    private fun reCalculatePaymentAmount() {
        paymentAmount = SeatsPayment(seats).getDiscountedMoneyByDateTime(reservation.bookedDateTime)
        updatePaymentAmount()
    }

    private fun updatePaymentAmount() {
        view.updateTextChoicePaymentAmount(paymentAmount.value)
    }

    private fun indexToRow(index: Int): SeatRow {
        val rows = SeatRow.values().toList()
        return rows[index / THEATER_COLUMN]
    }

    override fun getLocation(row: Int, column: Int): Location = Location(indexToRow(row), column)

    override fun getSeatGrade(location: Location): SeatGrade =
        theater.findSeat(location)?.grade ?: throw IllegalStateException(
            SEAT_NON_EXIST_ERROR.format("$location.row$location.number")
        )

    override fun getMovieById(movieId: Long): Movie = movieStorage.getMovieById(movieId)

    override fun getNotificationSettings() = settingStorage.getNotificationSettings()

    companion object {
        private const val INITIAL_PAYMENT_AMOUNT = 0

        const val THEATER_COLUMN = 4

        private val rows = SeatRow.values().toList()
        private val columns = List(THEATER_COLUMN) { it }

        private const val SEAT_NON_EXIST_ERROR = "%s seat는 존재하지 않습니다."
    }
}
