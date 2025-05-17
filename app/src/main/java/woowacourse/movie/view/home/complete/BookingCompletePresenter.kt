package woowacourse.movie.view.home.complete

import woowacourse.movie.data.setting.SettingRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.domain.model.ticket.Ticket
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val ticketRepository: TicketRepository,
    private val settingRepository: SettingRepository,
    private val ticket: Ticket,
) : BookingCompleteContract.Presenter {
    override fun loadTicket() {
        view.showTicket(ticket)
    }

    override fun decideNotification(ticket: Ticket) {
        val notificationEnabled: Boolean = settingRepository.isNotificationEnabled()
        val notificationPermitted: Boolean = view.isNotificationPermitted()
        if (!notificationEnabled) return
        if (!notificationPermitted) {
            view.notifyNoNotificationPermission()
            return
        }
        setNotification(ticket)
    }

    private fun setNotification(ticket: Ticket) {
        val screeningDateTime = LocalDateTime.of(ticket.screeningDate, ticket.screeningTime)
        val notificationTime =
            screeningDateTime
                .minusMinutes(NOTIFY_AHEAD_MINUTES)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        view.setNotification(ticket, notificationTime)
    }

    override fun addToHistory(ticket: Ticket) {
        thread {
            ticketRepository.insert(ticket)
        }.join()
    }

    companion object {
        private const val NOTIFY_AHEAD_MINUTES = 30L
    }
}
