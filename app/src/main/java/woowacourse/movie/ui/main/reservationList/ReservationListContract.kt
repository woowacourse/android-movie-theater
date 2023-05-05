package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.model.TicketsState

interface ReservationListContract {
    interface View {
        fun setAdapter(tickets: List<TicketsState>)
    }

    interface Presenter {
        fun getReservationList()
    }
}
