package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.ticket.TicketHistory

interface SettingContract {
    interface Presenter {
        fun presentScreen()

        fun setIsTicketAlarmChecked(isTicketAlarmChecked: Boolean)

        fun setNotification()

        fun deleteNotification()
    }

    interface View {
        fun switchAlarmSetting(isTicketAlarmChecked: Boolean)

        fun cancelAllAlarms(ticketHistories: List<TicketHistory>)

        fun scheduleAllAlarms(ticketHistories: List<TicketHistory>)
    }
}
