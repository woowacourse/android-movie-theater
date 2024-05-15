package woowacourse.movie.list.contract

import woowacourse.movie.ticket.model.TicketEntity

interface ReservationHistoryContract {
    interface View {
        fun showData(tickets: List<TicketEntity>)
    }

    interface Presenter {
        fun loadDataFromDb()
    }
}
