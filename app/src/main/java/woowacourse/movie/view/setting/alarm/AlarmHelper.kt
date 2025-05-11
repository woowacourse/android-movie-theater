package woowacourse.movie.view.setting.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
        title: String,
        dateTime: LocalDateTime,
    ) {
        if (!canScheduleExactAlarm(context)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("MOVIE_TITLE", title)
            }
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarmTime = convertToMillis(dateTime.minusMinutes(30))

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

    // 알림 취소

    private fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 1000, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
