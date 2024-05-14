package woowacourse.movie.ui.setting

import woowacourse.movie.model.data.MovieSharedPreferenceImpl
import woowacourse.movie.model.db.UserTicketRepository
import woowacourse.movie.model.notification.MovieAlarmManager

class MovieSettingPresenter(
    private val view: MovieSettingContract.View,
    private val userTicketRepository: UserTicketRepository,
) :
    MovieSettingContract.Presenter {
    override fun loadNotificationStatus() {
        view.showNotificationStatus(MovieSharedPreferenceImpl.getAlarmChecked())
    }

    override fun cancelNotification() {
        Thread {
            val userTickets = userTicketRepository.findAll()
            userTickets.forEach { userTicket ->
                MovieAlarmManager.get().cancelAlarm(userTicket.id.toInt())
            }
        }.start()
    }

    override fun setAlarmStatus(status: Boolean) {
        MovieSharedPreferenceImpl.setAlarmChecked(status)
    }
}
