package woowacourse.movie.view.home.complete

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingCompleteContract {
    interface View {
        fun showTicket(ticket: Ticket)

        fun setNotification(
            ticket: Ticket,
            time: Long,
        )
    }

    interface Presenter {
        fun loadTicket()

        fun loadNotificationInfo(ticket: Ticket)
    }
}
