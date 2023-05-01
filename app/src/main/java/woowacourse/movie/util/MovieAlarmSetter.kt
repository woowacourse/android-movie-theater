package woowacourse.movie.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.feature.confirm.AlarmReceiver
import woowacourse.movie.model.TicketsState
import java.util.Calendar

object MovieAlarmSetter {
    fun setAlarm(context: Context, tickets: TicketsState) {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(
                tickets.dateTime.year,
                tickets.dateTime.monthValue - 1,
                tickets.dateTime.dayOfMonth,
                tickets.dateTime.hour,
                tickets.dateTime.minute
            )
        }

        val alarmManager: AlarmManager =
            context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        val alarmIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            tickets.hashCode(),
            AlarmReceiver.getIntent(context, tickets),
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis - NOTIFICATION_TIME_ADJUST_AMOUNT,
            alarmIntent
        )
    }

    private const val NOTIFICATION_TIME_ADJUST_AMOUNT = 30 * 60 * 1000
}
