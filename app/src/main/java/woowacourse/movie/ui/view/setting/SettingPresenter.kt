package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.datasource.SettingsDataSource
import woowacourse.movie.domain.datasource.TicketDataSource
import kotlin.concurrent.thread

class SettingPresenter(
    private val ticketDataSource: TicketDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val view: SettingContract.View,
) : SettingContract.Presenter {
    init {
        view.switchAlarmSetting(settingsDataSource.isTicketAlarmChecked)
    }

    override fun setIsTicketAlarmChecked(isTicketAlarmChecked: Boolean) {
        settingsDataSource.setTicketAlarmChecked(isTicketAlarmChecked)
    }

    override fun scheduleAlarms() {
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
