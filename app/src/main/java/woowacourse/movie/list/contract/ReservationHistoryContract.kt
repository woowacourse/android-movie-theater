package woowacourse.movie.list.contract

import woowacourse.movie.ticket.model.DbTicket

interface ReservationHistoryContract {
    interface View {
        val presenter: Presenter
        fun linkReservationHistoryAdapter(tickets: List<DbTicket>)
        fun showReservationHistoryList()
        fun updateItems(items: List<DbTicket>)
    }

    interface Presenter {
        val view: View
    }
}
