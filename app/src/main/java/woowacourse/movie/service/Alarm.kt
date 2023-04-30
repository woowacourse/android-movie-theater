package woowacourse.movie.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import java.time.LocalDateTime

object Alarm {

    fun registerAlarmReceiver(
        action: String,
        alarmReceiver: ReservationAlarmReceiver,
        context: Context
    ) {
        val filter = IntentFilter().apply {
            addAction(action)
        }
        context.registerReceiver(alarmReceiver, filter)
    }

    fun unregisterAlarmReceiver(alarmReceiver: ReservationAlarmReceiver, context: Context) {
        context.unregisterReceiver(alarmReceiver)
    }

    fun makeAlarm(date: LocalDateTime, intent: PendingIntent, context: Context) {
//        val milliseconds = date.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
        val milliseconds = System.currentTimeMillis() + 8 * 1000
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, milliseconds, intent)
    }
}
