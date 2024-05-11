package woowacourse.movie.presentation.main.setting

import androidx.fragment.app.Fragment
import woowacourse.movie.data.utils.NotificationScheduler
import woowacourse.movie.data.utils.SharedPreferencesHelper
import woowacourse.movie.domain.repository.MovieTicketRepository
import woowacourse.movie.presentation.util.dateToString
import woowacourse.movie.presentation.util.screeningStringToDateTime
import woowacourse.movie.presentation.util.timeToString
import java.time.LocalDateTime

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: MovieTicketRepository,
) : SettingContract.Presenter {

    override fun onAlarmSwitchChanged(isChecked: Boolean) {
        if (isChecked) {
            view.requestNotificationPermission()
        } else {
            SharedPreferencesHelper.setNotificationEnabled((view as Fragment).requireContext(), false)
            view.cancelAllNotifications()
        }
    }

    override fun onPermissionResult(isGranted: Boolean) {
        val context = (view as Fragment).requireContext()
        if (isGranted) {
            SharedPreferencesHelper.setNotificationEnabled(context, true)
            view.showMessage("알림 권한이 허용되었습니다.")
            updateAllNotifications()
        } else {
            view.setAlarmSwitchChecked(false)
            SharedPreferencesHelper.setNotificationEnabled(context, false)
            view.showMessage("알림 권한이 거부되었습니다.")
            cancelAllNotifications()
        }
    }

    override fun initializeSettings() {
        val context = (view as Fragment).requireContext()
        val isEnabled = SharedPreferencesHelper.isNotificationEnabled(context)
        view.setAlarmSwitchChecked(isEnabled)
    }

    override fun updateAllNotifications() {
        val currentTime = LocalDateTime.now()
        val tickets = repository.findAll()

        tickets.forEach { ticket ->
            try {
                val screeningDateTime = screeningStringToDateTime(ticket.screeningDate, ticket.screeningTime)

                if (screeningDateTime.isAfter(currentTime)) {
                    NotificationScheduler.scheduleNotification(
                        (view as Fragment).requireContext(),
                        ticket.id,
                        screeningDateTime.dateToString() + " " + screeningDateTime.toLocalTime().timeToString()
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun cancelAllNotifications() {
        val tickets = repository.findAll()
        tickets.forEach { ticket ->
            NotificationScheduler.cancelNotification(
                (view as Fragment).requireContext(),
                ticket.id
            )
        }
    }
}
