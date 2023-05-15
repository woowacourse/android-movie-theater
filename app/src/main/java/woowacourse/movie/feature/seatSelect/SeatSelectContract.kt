package woowacourse.movie.feature.seatSelect

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState
import java.time.LocalDateTime

interface SeatSelectContract {
    interface View {
        fun seatToggle(index: Int)
        fun changePredictMoney(moneyState: MoneyState)
        fun setConfirmClickable(clickable: Boolean)
        fun showAskScreen()
        fun navigateReservationConfirm(tickets: TicketsState)
        fun setReservationAlarm(
            tickets: TicketsState,
            triggerDateTime: LocalDateTime,
            requestCode: Int
        )
        fun errorAddReservationTickets()
    }

    interface Presenter {
        val seats: List<SeatPositionState>
        fun clickSeat(index: Int)
        fun clickConfirm()
        fun clickAskPageConfirm()
        fun updateChosenSeats(chosen: List<SeatPositionState>)
    }
}
