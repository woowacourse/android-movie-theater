package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.base.BaseContract

interface ReservationListContract {
    interface View {
        fun showTickets(tickets: List<TicketsState>)
    }

    interface Presenter : BaseContract.Presenter {
        fun setUpReservationList()
    }
}
