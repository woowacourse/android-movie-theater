package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.ticket.Ticket

interface SettingContract {
    interface Presenter {
        fun presentScreen()

        fun setNotification()

        fun deleteNotification()
    }

    interface View {
        fun switchAlarmSetting()

        fun cancelAllAlarms(tickets: List<Ticket>)

        fun scheduleAllAlarms(tickets: List<Ticket>)
    }
}
