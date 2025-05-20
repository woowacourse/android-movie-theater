package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.ticket.TicketHistory

interface SettingContract {
    interface Presenter {
        fun setIsTicketAlarmChecked(isTicketAlarmChecked: Boolean)

        fun scheduleAlarms()

        fun deleteNotification()
    }

    interface View {
        fun switchAlarmSetting(isTicketAlarmChecked: Boolean)

        fun cancelAllAlarms(ticketHistories: List<TicketHistory>)

        fun scheduleAllAlarms(ticketHistories: List<TicketHistory>)
    }
}
