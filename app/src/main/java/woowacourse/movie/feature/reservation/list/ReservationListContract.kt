package woowacourse.movie.feature.reservation.list

import woowacourse.movie.model.TicketsState

interface ReservationListContract {
    interface View {
        fun setTicketList(items: List<TicketsState>)
        fun showTicketsConfirm(tickets: TicketsState)
    }

    interface Presenter {
        fun loadListItems()
        fun showTicketsConfirm(tickets: TicketsState)
    }
}
