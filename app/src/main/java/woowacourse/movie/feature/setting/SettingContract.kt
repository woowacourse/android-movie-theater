package woowacourse.movie.feature.setting

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.util.BasePresenter

interface SettingContract {
    interface View {
        fun initializeSwitch(isGrant: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadNotificationGrant()

        fun updateNotificationGrant(isGrant: Boolean)

        fun setTicketsAlarm()

        fun cancelTicketsAlarm()
    }
}
