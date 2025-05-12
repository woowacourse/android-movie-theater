package woowacourse.movie.view.home.complete

import woowacourse.movie.data.setting.SettingStorageManager
import woowacourse.movie.domain.model.ticket.Ticket
import java.time.LocalDateTime
import java.time.ZoneId

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val ticket: Ticket,
    private val manager: SettingStorageManager,
) : BookingCompleteContract.Presenter {
    override fun loadTicket() {
        view.showTicket(ticket)
    }

    override fun loadNotificationInfo(ticket: Ticket) {
        val enabled: Boolean = manager.isNotificationEnabled()
        if (!enabled) return

        val screeningDateTime = LocalDateTime.of(ticket.screeningDate, ticket.screeningTime)
        val notificationTime =
            screeningDateTime
                .minusMinutes(NOTIFY_AHEAD_MINUTES)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        view.setNotification(ticket, notificationTime)
    }

    companion object {
        private const val NOTIFY_AHEAD_MINUTES = 30L
    }
}
