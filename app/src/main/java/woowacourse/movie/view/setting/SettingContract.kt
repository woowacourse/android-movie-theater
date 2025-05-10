package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.Ticket

interface SettingContract {
    interface View {
        fun setNotification(tickets: List<Ticket>)

        fun setPermissionSwitch()

        fun showError(message: String)
    }

    interface Presenter {
        fun setNotification()

        fun setPermissionSwitch()
    }
}
