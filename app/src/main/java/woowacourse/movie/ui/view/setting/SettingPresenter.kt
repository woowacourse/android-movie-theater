package woowacourse.movie.ui.view.setting

import woowacourse.movie.domain.datasource.SettingsDataSource
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.ticket.TicketHistory

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
        ticketDataSource.getAll { tickets: List<TicketHistory> ->
            view.scheduleAllAlarms(tickets)
        }
    }

    override fun deleteNotification() {
        ticketDataSource.getAll { tickets: List<TicketHistory> ->
            view.cancelAllAlarms(tickets)
        }
    }
}
