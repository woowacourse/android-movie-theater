package woowacourse.movie.ui.seat

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState

interface SeatSelectContract {
    interface View {
        fun setMoneyText(money: MoneyState)
    }

    interface Presenter {
        fun discountApply(tickets: TicketsState)
    }
}
