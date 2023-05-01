package woowacourse.movie.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.Calendar

fun Context.setAlarm(
    intent: Intent,
    dateTime: LocalDateTime,
    requestCode: Int,
    timeMillsAdjustAmount: Long = 0L
) {
    val calendar: Calendar = dateTime.toCalendar()

    val alarmManager: AlarmManager =
        getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

    val alarmIntent: PendingIntent = getBroadcastPendingIntent(requestCode, intent)

    val triggerAtMillis: Long = calendar.timeInMillis - timeMillsAdjustAmount
    alarmManager.setRtcTimeMillsAlarm(triggerAtMillis, alarmIntent)
}

private fun LocalDateTime.toCalendar(): Calendar {
    return Calendar.getInstance().apply {
        set(
            year,
            monthValue - 1,
            dayOfMonth,
            hour,
            minute
        )
    }
}

private fun Context.getBroadcastPendingIntent(requestCode: Int, intent: Intent): PendingIntent =
    PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

private fun AlarmManager.setRtcTimeMillsAlarm(triggerAtMillis: Long, intent: PendingIntent) =
    setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerAtMillis,
        intent
    )
