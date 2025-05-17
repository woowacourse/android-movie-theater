package woowacourse.movie.view.home.complete

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingCompleteContract {
    interface View {
        fun showTicket(ticket: Ticket)

        fun setNotification(
            ticket: Ticket,
            time: Long,
        )

        fun isNotificationPermitted(): Boolean

        fun notifyNoNotificationPermission()
    }

    interface Presenter {
        fun loadTicket()

        fun decideNotification(ticket: Ticket)

        fun addToHistory(ticket: Ticket)
    }
}
