package woowacourse.movie.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.data.local.datasource.SettingsDataSourceImpl
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.alarm.Alarm

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val settingsDataSource = SettingsDataSourceImpl.of(context)
        if (!settingsDataSource.isTicketAlarmChecked) return
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scheduleAlarmAllTicket(context)
        }
    }

    private fun scheduleAlarmAllTicket(context: Context) {
        val alarm = Alarm(context)
        val database = getMovieDatabase(context)
        val ticketDataSourceImpl: woowacourse.movie.domain.datasource.TicketDataSource =
            woowacourse.movie.data.local.datasource.TicketDataSourceImpl(database.ticketDao())
        ticketDataSourceImpl.getAll { tickets: List<TicketHistory> ->
            tickets.forEach { ticketHistory: TicketHistory ->
                alarm.scheduleTicketAlarm(ticketHistory)
            }
        }
    }
}
