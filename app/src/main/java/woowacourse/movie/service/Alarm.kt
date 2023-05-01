package woowacourse.movie.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.IntentFilter
import woowacourse.movie.activity.SeatSelectionActivity.Companion.ACTION_ALARM
import woowacourse.movie.view.data.ReservationViewData
import java.time.LocalDateTime

object Alarm {

    fun registerAlarmReceiver(
        alarmReceiver: ReservationAlarmReceiver,
        context: Context
    ) {
        val filter = IntentFilter().apply {
            addAction(ACTION_ALARM)
        }
        context.registerReceiver(alarmReceiver, filter)
    }

    fun unregisterAlarmReceiver(alarmReceiver: ReservationAlarmReceiver, context: Context) {
        context.unregisterReceiver(alarmReceiver)
    }

    fun makeAlarm(date: LocalDateTime, reservation: ReservationViewData, context: Context) {
        val pendingIntent: PendingIntent = ReservationAlarmReceiver.from(context, reservation)
//        val milliseconds = date.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
        val milliseconds = System.currentTimeMillis() + 5 * 1000
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, milliseconds, pendingIntent)
    }
}
