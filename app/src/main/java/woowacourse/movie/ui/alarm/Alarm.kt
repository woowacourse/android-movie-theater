package woowacourse.movie.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.receiver.AlarmReceiver
import java.time.ZoneId

class Alarm(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleTicketAlarm(ticketHistory: TicketHistory) {
        val pendingIntent: PendingIntent = ticketAlarmPendingIntent(ticketHistory) ?: return
        val triggerAtMillis: Long = ticketTriggerAtMillis(ticketHistory)
        scheduleExactAlarmIfPermitted(triggerAtMillis, pendingIntent)
    }

    private fun ticketAlarmPendingIntent(ticketHistory: TicketHistory): PendingIntent? {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(EXTRA_ALARM_TICKET_ID, ticketHistory.id)
                putExtra(ticketHistory.id.toString(), ticketHistory)
            }
        return PendingIntent.getBroadcast(
            context,
            ticketHistory.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun ticketTriggerAtMillis(ticketHistory: TicketHistory): Long {
        val triggerShowtime = ticketHistory.notifyBeforeMinutes()
        val zoneId = ZoneId.systemDefault()
        return triggerShowtime.atZone(zoneId).toInstant().toEpochMilli()
    }

    private fun scheduleExactAlarmIfPermitted(
        triggerAtMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        if (canScheduleExactAlarms()) {
            setTicketAlarmManager(triggerAtMillis, pendingIntent)
        }
    }

    private fun canScheduleExactAlarms(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) return alarmManager.canScheduleExactAlarms()
        return true
    }

    private fun setTicketAlarmManager(
        triggerAtMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent,
        )
    }

    fun cancelTicketAlarm(ticketId: Long) {
        val pendingIntent: PendingIntent = cancelTicketAlarmPendingIndent(ticketId)
        alarmManager.cancel(pendingIntent)
    }

    private fun cancelTicketAlarmPendingIndent(ticketId: Long): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            ticketId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        const val EXTRA_ALARM_TICKET_ID = "EXTRA_ALARM_TICKET_ID"
    }
}
