package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.Ticket
import java.time.LocalDateTime

interface SettingContract {
    interface View {
        fun setNotification(
            tickets: List<Ticket>,
            showTimes: List<LocalDateTime>,
        )

        fun setPermissionSwitch(isEnabled: Boolean)

        fun showError(message: String)

        fun cancelNotification(tickets: List<Ticket>)
    }

    interface Presenter {
        fun setNotification()

        fun setPermissionSwitch()

        fun savePushAlarmSetting(isEnabled: Boolean)

        fun cancelNotification()
    }
}
