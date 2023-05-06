package woowacourse.movie.feature.reservation.confirm

import woowacourse.movie.model.TicketsState

interface TicketsConfirmContract {
    interface View {
        fun setTitle()
        fun setScreeningDateTime()
        fun setTicketSeatInfo()
        fun setMoney(value: Int)
    }

    interface Presenter {
        fun setDiscountApplyMoney(tickets: TicketsState)
    }
}
