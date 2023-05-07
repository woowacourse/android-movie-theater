package woowacourse.movie.ui.confirm

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BaseContract

interface ReservationConfirmContract {
    interface View {
        fun setTicket(ticket: TicketsState)
        fun setMoney(money: MoneyState)
        fun registerNotification(ticket: TicketsState)
    }

    interface Presenter : BaseContract.Presenter {
        fun discountApplyMoney(ticket: TicketsState)
        fun setUpTicket()
    }
}
