package woowacourse.movie.presentation.activities.main.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import java.time.LocalDateTime
import java.time.ZoneId

object PushAlarmManager {

    const val PUSH_DATA_KEY = "push"
    const val PUSH_ACTION: String =
        "woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.PUSH_ACTION"

    fun set(context: Context, pendingIntent: PendingIntent, time: LocalDateTime, ago: Long) {
        val pushTime = time.minusMinutes(ago)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, pushTime, pendingIntent)
    }
}
