package woowacourse.movie.ui.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BaseContract

interface SeatSelectContract {
    interface View {
        fun initSeatTable(seatSelectState: SeatSelectState)
        fun showSeatSelectState(seatSelectState: SeatSelectState)
        fun setMoneyText(money: MoneyState)
        fun navigateToConfirmView(tickets: TicketsState)
    }

    interface Presenter : BaseContract.Presenter {
        fun getSeatSelectState()
        fun discountApply(positionStates: List<SeatPositionState>)
        fun addTicket(positionStates: List<SeatPositionState>)
        fun getRequireCount(): Int
    }
}
