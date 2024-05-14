package woowacourse.movie.feature.setting

import android.content.Context
import woowacourse.movie.data.MovieSharedPreferences
import woowacourse.movie.data.notification.NotificationRepository
import woowacourse.movie.data.notification.NotificationSharedPreferencesRepository
import woowacourse.movie.data.ticket.RoomTicketRepository
import woowacourse.movie.data.ticket.TicketDatabase
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.model.notification.TicketAlarm

class SettingPresenter(
    private val view: SettingContract.View,
    applicationContext: Context,
    private val notificationRepository: NotificationRepository =
        NotificationSharedPreferencesRepository.instance(MovieSharedPreferences.instance(applicationContext)),
    private val ticketAlarm: TicketAlarm = TicketAlarm(applicationContext),
    private val ticketRepository: TicketRepository =
        RoomTicketRepository(TicketDatabase.instance(applicationContext).ticketDao()),
) : SettingContract.Presenter {
    override fun loadNotificationGrant() {
        val isGrant = notificationRepository.isGrant()
        view.initializeSwitch(isGrant)
    }

    override fun updateNotificationGrant(isGrant: Boolean) {
        notificationRepository.update(isGrant)
    }

    override fun setTicketsAlarm() {
        val tickets = ticketRepository.findAll()
        ticketAlarm.setReservationAlarms(tickets)
    }

    override fun cancelTicketsAlarm() {
        val tickets = ticketRepository.findAll()
        ticketAlarm.cancelReservationAlarms(tickets)
    }
}
