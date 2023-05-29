package woowacourse.movie.feature.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.TicketsState

interface SeatSelectContract {

    interface View {
        fun setViewContents(selectReservationState: SelectReservationState)
        fun changeSeatCheckedByIndex(index: Int)
        fun changePredictMoney(moneyState: MoneyState)
        fun setConfirmClickable(clickable: Boolean)
        fun navigateReservationConfirm(tickets: TicketsState)
        fun setReservationAlarm(tickets: TicketsState)
        fun showReservationConfirmationDialog()
        fun showLoadError()
        fun finishActivity()
    }

    interface Presenter {
        val seats: List<SeatPositionState>
        fun loadViewContents()
        fun checkSeat(index: Int)
        fun showReservationConfirmationDialog()
        fun reserveTickets()
        fun updateChosenSeats(chosen: List<SeatPositionState>)
    }
}
