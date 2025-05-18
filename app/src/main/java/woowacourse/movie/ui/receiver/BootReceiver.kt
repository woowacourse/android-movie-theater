package woowacourse.movie.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.alarm.Alarm
import kotlin.concurrent.thread

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isTicketAlarmChecked = sharedPreferences.getBoolean("isTicketAlarm", false)
        if (!isTicketAlarmChecked) return
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scheduleAlarmAllTicket(context)
        }
    }

    private fun scheduleAlarmAllTicket(context: Context) {
        thread {
            val alarm = Alarm(context)
            val database = getMovieDatabase(context)
            val ticketDataSource: woowacourse.movie.domain.datasource.TicketDataSource =
                woowacourse.movie.data.local.datasource.TicketDataSource(database.ticketDao())
            val tickets = ticketDataSource.getAll()
            tickets.forEach { ticket: Ticket ->
                alarm.scheduleTicketAlarm(ticket)
            }
        }
    }
}
