package woowacourse.movie.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

fun Context.setAlarm(
    intent: Intent,
    triggerDateTime: LocalDateTime,
    requestCode: Int
) {
    val alarmManager: AlarmManager =
        getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

    val alarmIntent: PendingIntent = getBroadcastPendingIntent(requestCode, intent)

    val triggerAtMillis = triggerDateTime.toCalendar().timeInMillis
    alarmManager.setRtcTimeMillsAlarm(triggerAtMillis, alarmIntent)
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
