package woowacourse.movie.ui.view.history

import woowacourse.movie.domain.ticket.Ticket

interface ReservationHistoryContract {
    interface Presenter {
        fun presentScreen()
    }

    interface View {
        fun updateScreen(tickets: List<Ticket>)
    }
}
