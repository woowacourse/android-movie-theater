package woowacourse.movie.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import java.time.LocalDateTime
import java.util.TimeZone

object Alarm {

    fun makeAlarmReceiver(action: String, context: Context) {
        val filter = IntentFilter().apply {
            addAction(action)
        }
        context.registerReceiver(ReservationAlarmReceiver(), filter)
    }

    fun makeAlarm(date: LocalDateTime, intent: PendingIntent, context: Context) {
        val milliseconds = date.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, milliseconds, intent)
    }
}
