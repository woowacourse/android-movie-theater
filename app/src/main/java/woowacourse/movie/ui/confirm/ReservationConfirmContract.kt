package woowacourse.movie.ui.confirm

import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.TicketsState

interface ReservationConfirmContract {
    interface View {
        fun setMoneyTextView(money: MoneyState)
    }

    interface Presenter {
        fun setDiscountApplyMoney(tickets: TicketsState)
    }
}
