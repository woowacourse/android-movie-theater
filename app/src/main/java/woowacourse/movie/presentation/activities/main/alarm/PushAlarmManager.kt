package woowacourse.movie.presentation.activities.main.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.ZoneId

class PushAlarmManager<T : Parcelable>(
    context: Context,
    intent: Intent,
    data: T,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val pendingIntent = run {
        intent.action = PUSH_ACTION
        intent.putExtra(PUSH_DATA_KEY, data)
        PendingIntent.getBroadcast(context, getUniqueNumber(), intent, PendingIntent.FLAG_IMMUTABLE)
    }

    fun set(time: LocalDateTime, ago: Long) {
        val pushTime = time.minusMinutes(ago)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.set(AlarmManager.RTC_WAKEUP, pushTime, pendingIntent)
    }

    private fun getUniqueNumber(): Int = System.currentTimeMillis().toInt()

    companion object {
        internal const val PUSH_DATA_KEY = "push"
        internal const val PUSH_ACTION: String =
            "woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.PUSH_ACTION"
    }
}
