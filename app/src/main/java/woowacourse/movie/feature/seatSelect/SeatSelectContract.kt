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
        fun showDialog()
        fun navigateReservationConfirm(tickets: TicketsState)
        fun setReservationAlarm(
            tickets: TicketsState,
            triggerDateTime: LocalDateTime,
            requestCode: Int
        )

        // todo: 예약 실패 시 띄울 토스트 만들어야 함
    }

    interface Presenter {
        val seats: List<SeatPositionState>
        fun clickSeat(index: Int)
        fun clickConfirm()
        fun clickDialogConfirm()
        fun updateChosenSeats(chosen: List<SeatPositionState>)
    }
}
