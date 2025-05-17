package woowacourse.movie.view.notification

import woowacourse.movie.domain.model.ticket.Ticket
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationScheduler {
    fun notificationTime(ticket: Ticket): Long {
        val screeningDateTime = LocalDateTime.of(ticket.screeningDate, ticket.screeningTime)
        return screeningDateTime
            .minusMinutes(NOTIFY_AHEAD_MINUTES)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    companion object {
        private const val NOTIFY_AHEAD_MINUTES = 30L
    }
}
