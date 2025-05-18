package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.datasource.TicketDataSource
import kotlin.concurrent.thread

class SettingPresenter(
    private val ticketDataSource: TicketDataSource,
    private val view: SettingContract.View,
) : SettingContract.Presenter {
    override fun presentScreen() {
        view.switchAlarmSetting()
    }

    override fun setNotification() {
        thread {
            val tickets = ticketDataSource.getAll()
            view.scheduleAllAlarms(tickets)
        }
    }

    override fun deleteNotification() {
        thread {
            val tickets = ticketDataSource.getAll()
            view.cancelAllAlarms(tickets)
        }
    }
}
