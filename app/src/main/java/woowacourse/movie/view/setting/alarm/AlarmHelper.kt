package woowacourse.movie.view.setting.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.movieseat.RemainTimePolicy
import woowacourse.movie.view.reservation.TicketUi
import java.time.LocalDateTime
import java.time.ZoneId

object AlarmHelper {
    private fun canScheduleExactAlarm(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    fun setAlarm(
        context: Context,
        ticketUi: TicketUi,
    ) {
        if (!canScheduleExactAlarm(context)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ticket", ticketUi)
            }
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmTime = convertToMillis(ticketUi.date.minusMinutes(RemainTimePolicy.NormalPolicy.time.minute.toLong()))

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent,
        )
    }

    private fun convertToMillis(localDateTime: LocalDateTime): Long {
        val zoneId = ZoneId.systemDefault()
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }
}
