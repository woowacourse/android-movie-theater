package woowacourse.movie.feature.setting

import android.content.Context
import woowacourse.movie.data.notification.NotificationRepository
import woowacourse.movie.data.notification.NotificationSharedPreferencesRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.notification.TicketAlarm
import kotlin.concurrent.thread

class SettingPresenter(
    private val view: SettingContract.View,
    applicationContext: Context,
    private val notificationRepository: NotificationRepository =
        NotificationSharedPreferencesRepository.instance(applicationContext),
    private val ticketAlarm: TicketAlarm = TicketAlarm(applicationContext),
) : SettingContract.Presenter {
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
        ticketAlarm.setReservationAlarms(tickets)
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        ticketAlarm.cancelReservationAlarms(tickets)
    }
}
