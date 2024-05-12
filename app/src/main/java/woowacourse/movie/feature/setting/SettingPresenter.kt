package woowacourse.movie.feature.setting

import android.content.Context
import woowacourse.movie.data.notification.NotificationRepository
import woowacourse.movie.data.notification.NotificationSharedPreferencesRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class SettingPresenter(
    private val view: SettingContract.View,
    applicationContext: Context,
) : SettingContract.Presenter {
    private val notificationRepository: NotificationRepository
            by lazy { NotificationSharedPreferencesRepository.instance(applicationContext) }

    override fun loadNotificationGrant() {
        val isGrant = notificationRepository.isGrant()
        view.initializeSwitch(isGrant)
    }

    override fun updateNotificationGrant(isGrant: Boolean) {
        notificationRepository.update(isGrant)
    }

    override fun setTicketsAlarm(ticketRepository: TicketRepository) {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        tickets.forEach { view.setTicketAlarm(it) }
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        tickets.forEach { view.cancelTicketAlarm(it) }
    }
}
