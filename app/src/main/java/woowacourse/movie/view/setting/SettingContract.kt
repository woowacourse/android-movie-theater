package woowacourse.movie.view.setting

import woowacourse.movie.domain.model.Ticket

interface SettingContract {
    interface View {
        fun setNotification(tickets: List<Ticket>)

        fun setPermissionSwitch()
    }

    interface Presenter {
        fun setNotification()

        fun setPermissionSwitch()
    }
}
