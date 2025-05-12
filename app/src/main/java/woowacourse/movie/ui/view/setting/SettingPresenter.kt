package woowacourse.movie.ui.view.setting

import woowacourse.movie.ui.view.data.TicketDataAdapter
import kotlin.concurrent.thread

class SettingPresenter(
    private val ticketDataAdapter: TicketDataAdapter,
    private val view: SettingContract.View,
) : SettingContract.Presenter {
    override fun presentScreen() {
        view.switchAlarmSetting()
    }

    override fun setNotification() {
        thread {
            val tickets = ticketDataAdapter.getAll()
            view.scheduleAllAlarms(tickets)
        }
    }

    override fun deleteNotification() {
        thread {
            val tickets = ticketDataAdapter.getAll()
            view.cancelAllAlarms(tickets)
        }
    }
}
