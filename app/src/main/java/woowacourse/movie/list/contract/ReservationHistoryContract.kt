package woowacourse.movie.list.contract

import woowacourse.movie.ticket.model.DbTicket

interface ReservationHistoryContract {
    interface View {
        fun showData(tickets: List<DbTicket>)
    }

    interface Presenter {
        fun loadDataFromDb()
    }
}
