package woowacourse.movie.view.setting.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.Ticket
import java.time.LocalDateTime
import java.time.ZoneId

object AlarmHelper {
    // 알람 권한 체크
    private fun canScheduleExactAlarm(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    // 알림 설정 (등록)
    fun setAlarm(
        context: Context,
        ticket: Ticket,
    ) {
        if (!canScheduleExactAlarm(context)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("TICKET", ticket)
            }
        val requestCode = ticket.hashCode()

        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmTime = convertToMillis(ticket.dateTime.minusMinutes(30))

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
