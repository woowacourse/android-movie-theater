package woowacourse.movie.feature.seatSelect

import com.example.domain.usecase.DiscountApplyUseCase
import com.example.domain.usecase.GetIssuedTicketsUseCase
import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
    val reservationState: ReservationState,
    val ticketsRepository: TicketsRepository
) : SeatSelectContract.Presenter {
    private val discountApplyUseCase = DiscountApplyUseCase()
    private val getIssuedTicketsUseCase = GetIssuedTicketsUseCase()

    private var _seats: MutableList<SeatPositionState> = mutableListOf()
    override val seats: List<SeatPositionState>
        get() = _seats.toList()

    override fun clickSeat(index: Int) {
        val seatPosition = convertIndexToPosition(index)
        if (seatPosition !in seats && seats.size >= reservationState.countState.value) return
        if (seatPosition in _seats) _seats.remove(seatPosition)
        else _seats.add(seatPosition)
        view.seatToggle(index)

        updateMoneyAndConfirmBtnState()
    }

    override fun clickConfirm() {
        view.showDialog()
    }

    override fun clickDialogConfirm() {
        val tickets = getIssuedTicketsUseCase(
            reservationState.movieState.asDomain(),
            reservationState.dateTime,
            seats.map { it.asDomain() }
        ).asPresentation()

        view.setReservationAlarm(
            tickets,
            tickets.dateTime.minusMinutes(NOTIFICATION_ADJUST_MINUTES),
            tickets.hashCode()
        )
        ticketsRepository.addTicket(tickets)
        view.navigateReservationConfirm(tickets)
    }

    override fun updateChosenSeats(chosen: List<SeatPositionState>) {
        _seats.forEach {
            view.seatToggle(convertPositionToIndex(it))
        }
        _seats = chosen.toMutableList()
        updateMoneyAndConfirmBtnState()
    }

    private fun updateMoneyAndConfirmBtnState() {
        val money = discountApplyUseCase(
            reservationState.movieState.asDomain(),
            reservationState.dateTime,
            seats.map { it.asDomain() }
        ).asPresentation()
        view.changePredictMoney(money)

        view.setConfirmClickable(seats.size == reservationState.countState.value)
    }

    private fun convertIndexToPosition(index: Int): SeatPositionState {
        val row = index / COLUMN_SIZE + 1
        val column = (index - (row - 1) * COLUMN_SIZE) + 1
        return SeatPositionState(row, column)
    }

    private fun convertPositionToIndex(seatPositionState: SeatPositionState): Int {
        return (seatPositionState.row - 1) * COLUMN_SIZE + (seatPositionState.column - 1)
    }

    companion object {
        private const val ROW_SIZE = 5
        private const val COLUMN_SIZE = 4

        private const val NOTIFICATION_ADJUST_MINUTES: Long = 30L
    }
}
