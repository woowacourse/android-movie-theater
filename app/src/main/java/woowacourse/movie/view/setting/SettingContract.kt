package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.Ticket
import java.time.LocalDateTime

interface SettingContract {
    interface View {
        fun setNotification(
            tickets: List<Ticket>,
            showTimes: List<LocalDateTime>,
        )

        fun setPermissionSwitch()

        fun showError(message: String)
    }

    interface Presenter {
        fun setNotification()

        fun setPermissionSwitch()
    }
}
