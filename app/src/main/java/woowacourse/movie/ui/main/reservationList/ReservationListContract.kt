package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BaseContract

interface ReservationListContract {
    interface View {
        fun setTickets(tickets: List<TicketsState>)
    }

    interface Presenter : BaseContract.Presenter {
        fun setUpReservationList()
    }
}
