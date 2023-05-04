package woowacourse.movie.feature.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.model.TicketsState
import java.util.Calendar

class MovieRemainder {

    fun set(context: Context, tickets: TicketsState) {
        val alarmManager: AlarmManager =
            context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getSchedule(tickets).timeInMillis - 30 * 60 * 1000,
            getAlarmIntent(context, tickets)
        )
    }

    private fun getSchedule(tickets: TicketsState): Calendar {
        return Calendar.getInstance().apply {
            set(
                tickets.dateTime.year,
                tickets.dateTime.monthValue - 1,
                tickets.dateTime.dayOfMonth,
                tickets.dateTime.hour,
                tickets.dateTime.minute
            )
        }
    }

    private fun getAlarmIntent(context: Context, tickets: TicketsState): PendingIntent {
        val intent: Intent = Intent(context, MovieReminderReceiver::class.java)
        intent.putExtra(MovieReminderReceiver.KEY_TICKETS, tickets)

        return PendingIntent.getBroadcast(
            context,
            tickets.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private var instance: MovieRemainder? = null

        fun getInstance(): MovieRemainder {
            return instance ?: synchronized(this) {
                instance ?: MovieRemainder().also {
                    instance = it
                }
            }
        }
    }
}
