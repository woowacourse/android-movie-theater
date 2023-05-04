package woowacourse.movie.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE

object AlarmManager {
    fun setAlarm(context: Context, time: Long, intent: PendingIntent) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            intent
        )
    }
}
