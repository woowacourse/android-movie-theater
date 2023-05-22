package woowacourse.movie.feature.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

interface SeatSelectContract {
    interface View {
        fun seatToggle(index: Int)
        fun changePredictMoney(moneyState: MoneyState)
        fun setConfirmClickable(clickable: Boolean)
        fun showAskScreen()
        fun navigateReservationConfirm(tickets: TicketsState)
        fun setReservationAlarm(tickets: TicketsState)
    }

    interface Presenter {
        val seats: List<SeatPositionState>
        fun clickSeat(index: Int)
        fun clickConfirm()
        fun clickAskPageConfirm()
        fun updateChosenSeats(chosen: List<SeatPositionState>)
    }
}
