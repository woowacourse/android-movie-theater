package woowacourse.movie.model.movie

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.MoviePreferencesUtil
import woowacourse.movie.model.movie.AlarmScheduler.Companion.EXTRA_SUBTITLE
import woowacourse.movie.model.movie.AlarmScheduler.Companion.EXTRA_TITLE
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

class AlarmScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java) as AlarmManager

    fun setSchedule(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java)
            .setAction("alert")
            .putExtra(EXTRA_TITLE, item.title)
            .putExtra(EXTRA_SUBTITLE, item.subTitle)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                ZonedDateTime.of(item.dateTime.minusMinutes(30), ZoneId.systemDefault()).toInstant()
                .toEpochMilli(),
                pendingIntent
            ),
            pendingIntent,
        )
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_SUBTITLE = "subtitle"
    }
}
