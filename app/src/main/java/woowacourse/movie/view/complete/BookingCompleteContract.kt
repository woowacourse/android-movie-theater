package woowacourse.movie.view.complete

import woowacourse.movie.domain.model.Ticket

interface BookingCompleteContract {
    interface View {
        fun showTicket(ticket: Ticket)
        fun generateAlarm(ticket: Ticket)

        fun showMessage()
    }

    interface Presenter {
        fun loadTicket(
            ticketId: Long,
            requestAlarm: Boolean,
        )
    }

    interface Presenter {
        fun loadTicket()
    }
}
