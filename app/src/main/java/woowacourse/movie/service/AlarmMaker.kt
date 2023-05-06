package woowacourse.movie.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.view.data.ReservationViewData
import java.time.LocalDateTime

object AlarmMaker {

    fun make(date: LocalDateTime, reservation: ReservationViewData, context: Context) {
        val pendingIntent: PendingIntent = ReservationAlarmReceiver.from(context, reservation)
//        val milliseconds = date.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli()
        val milliseconds = System.currentTimeMillis() + 10 * 1000
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC, milliseconds, pendingIntent)
    }
}
