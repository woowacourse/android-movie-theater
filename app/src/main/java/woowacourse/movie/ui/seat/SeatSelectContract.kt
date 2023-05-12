package woowacourse.movie.ui.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.base.BaseContract

interface SeatSelectContract {
    interface View {
        fun initSeatTable(seatSelectState: SeatSelectState)
        fun showReservationTitle(seatSelectState: SeatSelectState)
        fun showMoneyText(money: MoneyState)
        fun setConfirmClickable(clickable: Boolean)
        fun setReservationConfirm(seatSelectState: SeatSelectState)
        fun navigateToConfirmView(tickets: TicketsState)
    }

    interface Presenter : BaseContract.Presenter {
        fun setUpSeatSelectState()
        fun discountMoneyApply(positionStates: List<SeatPositionState>)
        fun addTicket(positionStates: List<SeatPositionState>)
    }
}
