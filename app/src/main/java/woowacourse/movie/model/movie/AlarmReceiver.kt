package woowacourse.movie.model.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.MoviePreferencesUtil
import woowacourse.movie.model.movie.AlarmScheduler.Companion.EXTRA_ID
import woowacourse.movie.model.movie.AlarmScheduler.Companion.EXTRA_SUBTITLE
import woowacourse.movie.model.movie.AlarmScheduler.Companion.EXTRA_TITLE
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.complete.MovieReservationCompleteKey
import woowacourse.movie.ui.main.MovieMainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val requestId = System.currentTimeMillis().toInt()
        val isNotificationEnabled =
            MoviePreferencesUtil(context ?: return).getBoolean("rcv_notification")
        if (isNotificationEnabled && intent?.action == "alert") {
            val id = intent.getLongExtra(EXTRA_ID, -1)
            val title = intent.getStringExtra(EXTRA_TITLE)
            val subtitle = intent.getStringExtra(EXTRA_SUBTITLE)
            val navigatingIntents = arrayOf(
                Intent(context, MovieMainActivity::class.java),
                Intent(context, MovieReservationCompleteActivity::class.java).putExtra(
                    MovieReservationCompleteKey.TICKET_ID,
                    id
                )
            )

            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID_RESERVATION,
                    CHANNEL_NAME_RESERVATION,
                    IMPORTANCE_HIGH
                )
            notificationManager.createNotificationChannel(notificationChannel)
            val notificationBuilder =
                NotificationCompat.Builder(context, CHANNEL_ID_RESERVATION)
                    .setContentTitle(title)
                    .setContentText(subtitle)
                    .setSmallIcon(R.drawable.ic_home)
                    .setContentIntent(
                        PendingIntent.getActivities(
                            context,
                            requestId,
                            navigatingIntents,
                            FLAG_IMMUTABLE,
                        )
                    )
            notificationManager.notify(requestId, notificationBuilder.build())
        }
    }

    companion object {
        private const val CHANNEL_ID_RESERVATION = "reservation_channel"
        private const val CHANNEL_NAME_RESERVATION = "reservation"
    }
}
