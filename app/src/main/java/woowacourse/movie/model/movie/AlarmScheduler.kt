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
        Log.i("Alarm", "setSchedule: $item")
        val intent =
            Intent(context, AlarmReceiver::class.java)
                .setAction("alert")
                .putExtra(EXTRA_TITLE, item.title)
                .putExtra(EXTRA_SUBTITLE, item.subTitle)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            ZonedDateTime.of(item.dateTime.minusMinutes(30), ZoneId.systemDefault()).toInstant()
                .toEpochMilli(),
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            ),
        )
    }

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_SUBTITLE = "subtitle"
    }
}

data class AlarmItem(
    val dateTime: LocalDateTime,
    val title: String,
    val subTitle: String,
)

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val isNotificationEnabled =
            MoviePreferencesUtil(context ?: return).getBoolean("rcv_notification")
        if (isNotificationEnabled && intent?.action == "alert") {
            val title = intent.getStringExtra(EXTRA_TITLE)
            val subtitle = intent.getStringExtra(EXTRA_SUBTITLE)
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID_RESERVATION,
                    CHANNEL_NAME_RESERVATION,
                    IMPORTANCE_HIGH,
                )
            notificationManager.createNotificationChannel(notificationChannel)
            val notifyBuilder =
                NotificationCompat.Builder(context, CHANNEL_ID_RESERVATION)
                    .setContentTitle(title)
                    .setContentText(subtitle)
                    .setSmallIcon(R.drawable.ic_home)
            notificationManager.notify(Random(100).nextInt(), notifyBuilder.build())
        }
    }

    companion object {
        private const val CHANNEL_ID_RESERVATION = "reservation_channel"
        private const val CHANNEL_NAME_RESERVATION = "reservation"
    }
}
