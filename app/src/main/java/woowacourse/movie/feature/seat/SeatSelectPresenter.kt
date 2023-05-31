package woowacourse.movie.feature.seat

import com.example.domain.model.TicketOffice
import com.example.domain.repository.TicketsRepository
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.mapper.asDomain
import woowacourse.movie.model.mapper.asPresentation

class SeatSelectPresenter(
    val view: SeatSelectContract.View,
    reservationState: SelectReservationState?,
    private val ticketOffice: TicketOffice,
    private val ticketsRepository: TicketsRepository
) : SeatSelectContract.Presenter {

    private var _seats: MutableList<SeatPositionState> = mutableListOf()
    override val seats: List<SeatPositionState>
        get() = _seats.sortedWith(compareBy<SeatPositionState> { it.row }.thenBy { it.column })

    private val reservationState: SelectReservationState = reservationState!!

    init {
        if (reservationState == null) {
            view.showLoadError()
            view.finishActivity()
        } else {
            loadViewContents()
        }
    }

    override fun loadViewContents() {
        view.setViewContents(reservationState)
    }

    init {
        updateMoneyAndConfirmBtnState()
    }

    override fun checkSeat(index: Int) {
        val seatPosition = getSeatPositionByIndex(index)
        if (seatPosition !in seats && seats.size >= reservationState.selectCount) return

        if (seatPosition in _seats) _seats.remove(seatPosition)
        else _seats.add(seatPosition)

        view.changeSeatCheckedByIndex(index)
        updateMoneyAndConfirmBtnState()
    }

    override fun showReservationConfirmationDialog() {
        view.showReservationConfirmationDialog()
    }

    override fun reserveTickets() {
        val tickets = ticketOffice.issueTickets(
            reservationState.theater.asDomain(),
            reservationState.movie.asDomain(),
            reservationState.selectDateTime,
            seats.map { it.asDomain() }
        ).asPresentation()

        ticketsRepository.addTicket(tickets.asDomain())
        view.setReservationAlarm(tickets)
        view.navigateReservationConfirm(tickets)
    }

    override fun updateChosenSeats(chosen: List<SeatPositionState>) {
        clear()
        chosen.forEach {
            checkSeat(getIndexBySeatPosition(it))
        }
        updateMoneyAndConfirmBtnState()
    }

    private fun clear() {
        val alreadyChosenIndexes = _seats.map { getIndexBySeatPosition(it) }
        alreadyChosenIndexes.forEach {
            checkSeat(it)
        }
    }

    private fun updateMoneyAndConfirmBtnState() {
        val money = ticketOffice.predictMoney(
            reservationState.movie.asDomain(),
            reservationState.selectDateTime,
            seats.map { it.asDomain() }
        ).asPresentation()
        view.changePredictMoney(money)

        view.setConfirmClickable(seats.size == reservationState.selectCount)
    }

    private fun getSeatPositionByIndex(index: Int): SeatPositionState {
        val row = index / COLUMN_SIZE + 1
        val column = (index - (row - 1) * COLUMN_SIZE) + 1
        return SeatPositionState(row, column)
    }

    private fun getIndexBySeatPosition(seatPositionState: SeatPositionState): Int {
        return (seatPositionState.row - 1) * COLUMN_SIZE + (seatPositionState.column - 1)
    }

    companion object {
        private const val ROW_SIZE = 5
        private const val COLUMN_SIZE = 4

        private const val NOTIFICATION_ADJUST_MINUTES: Long = 30L
    }
}
