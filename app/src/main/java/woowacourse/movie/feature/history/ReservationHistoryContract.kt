package woowacourse.movie.feature.history

import woowacourse.movie.model.ticket.Ticket

interface ReservationHistoryContract {
    interface Presenter {
        fun loadTicket()
    }

    interface View {
        fun showReservationHistory(tickets: List<Ticket>)
    }
}
