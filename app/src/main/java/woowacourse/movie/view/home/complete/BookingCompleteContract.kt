package woowacourse.movie.view.home.complete

import woowacourse.movie.domain.model.Ticket

interface BookingCompleteContract {
    interface View {
        fun showTicket(ticket: Ticket)
    }

    interface Presenter {
        fun loadTicket()
    }
}
