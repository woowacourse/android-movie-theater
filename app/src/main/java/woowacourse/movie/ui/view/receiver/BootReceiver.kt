package woowacourse.movie.ui.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.local.adapter.TicketAdapter
import woowacourse.movie.data.local.database.MovieDatabase.Companion.getMovieDatabase
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.alarm.Alarm
import woowacourse.movie.ui.view.data.TicketDataAdapter
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
            thread {
                val alarm = Alarm(context)
                val database = getMovieDatabase(context)
                val ticketDataAdapter: TicketDataAdapter = TicketAdapter(database.ticketDao())
                val tickets = ticketDataAdapter.getAll()
                tickets.forEach { ticket: Ticket ->
                    alarm.scheduleTicketAlarm(ticket)
                }
            }
        }
    }
}
