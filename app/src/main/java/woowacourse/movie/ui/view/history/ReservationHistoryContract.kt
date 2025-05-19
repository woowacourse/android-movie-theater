package woowacourse.movie.ui.view.history

import woowacourse.movie.domain.ticket.TicketHistory

interface ReservationHistoryContract {
    interface Presenter {
        fun presentScreen()
    }

    interface View {
        fun updateScreen(ticketHistories: List<TicketHistory>)
    }
}
