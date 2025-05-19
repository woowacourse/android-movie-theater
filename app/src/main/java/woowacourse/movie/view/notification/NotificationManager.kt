package woowacourse.movie.view.notification

import woowacourse.movie.domain.model.ticket.Ticket

interface NotificationManager {
    fun setNotification(ticket: Ticket)
}
