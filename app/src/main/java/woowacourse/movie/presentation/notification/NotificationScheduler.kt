package woowacourse.movie.presentation.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.MovieApplication
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationScheduler(private val context: Context = MovieApplication.instance) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(
        ticket: MovieTicket,
        time: Long = 30L,
    ) {
        if (!canScheduleAlarm()) return

        val alarmTime = ticket.screeningDateTime.toTimeMillis(time)
        val pendingIntent = createPendingIntent(ticket, time)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent
        )
    }

    private fun canScheduleAlarm(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S ||
                alarmManager.canScheduleExactAlarms()
    }

    private fun createPendingIntent(
        ticket: MovieTicket,
        time: Long,
    ): PendingIntent {
        val intent = Intent(context, TicketNotificationReceiver::class.java).apply {
            putExtra(KEY_TICKET, ticket)
            putExtra(KEY_TIME, time)
        }

        return PendingIntent.getBroadcast(
            context,
            ticket.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun LocalDateTime.toTimeMillis(time: Long): Long {
        return this.minusMinutes(time)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    companion object {
        const val KEY_TICKET = "Ticket"
        const val KEY_TIME = "Time"
    }
}