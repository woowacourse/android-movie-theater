package woowacourse.movie.view.home.complete

import woowacourse.movie.data.setting.SettingRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.notification.NotificationManager
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val ticketRepository: TicketRepository,
    private val settingRepository: SettingRepository,
    private val notificationManager: NotificationManager,
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
        notificationManager.setNotification(ticket)
    }

    override fun addToHistory(ticket: Ticket) {
        thread {
            ticketRepository.insert(ticket)
        }.join()
    }
}
