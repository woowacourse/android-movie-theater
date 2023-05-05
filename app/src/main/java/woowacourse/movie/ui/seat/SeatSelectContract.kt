package woowacourse.movie.ui.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState

interface SeatSelectContract {
    interface View {
        fun initSeatTable(seatSelectState: SeatSelectState)
        fun showSeatSelectState(seatSelectState: SeatSelectState)
        fun setMoneyText(money: MoneyState)
        fun navigateToConfirmView(tickets: TicketsState)
    }

    interface Presenter {
        fun discountApply(positionStates: List<SeatPositionState>)
        fun addTicket(positionStates: List<SeatPositionState>)
        fun getRequireCount(): Int
    }
}
