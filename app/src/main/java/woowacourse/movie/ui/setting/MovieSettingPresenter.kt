package woowacourse.movie.ui.setting

import android.content.Context
import woowacourse.movie.model.db.UserTicketRepository
import woowacourse.movie.ui.setting.notification.MovieAlarmManager

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val userTicketRepository: UserTicketRepository,
) :
    MovieSettingContract.Presenter {
    override fun loadNotificationStatus() {
        view.showNotificationStatus()
    }

    override fun cancelNotification(context: Context) {
        Thread {
            val userTickets = userTicketRepository.findAll()
            userTickets.forEach { userTicket ->
                MovieAlarmManager.cancelAlarm(context, userTicket.id.toInt())
            }
        }.start()
    }
}
