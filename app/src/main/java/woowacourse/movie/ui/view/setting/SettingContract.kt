package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.ticket.Ticket

interface SettingContract {
    interface Presenter {
        fun presentScreen()

        fun setIsTicketAlarmChecked(isTicketAlarmChecked: Boolean)

        fun setNotification()

        fun deleteNotification()
    }

    interface View {
        fun switchAlarmSetting(isTicketAlarmChecked: Boolean)

        fun cancelAllAlarms(tickets: List<Ticket>)

        fun scheduleAllAlarms(tickets: List<Ticket>)
    }
}
