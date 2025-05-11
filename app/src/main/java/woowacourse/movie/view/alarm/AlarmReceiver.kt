package woowacourse.movie.view.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.view.alarm.AlarmFactory.Companion.MOVIE_NOTIFICATION_CHANNEL_ID
import woowacourse.movie.view.alarm.AlarmFactory.Companion.MOVIE_TITLE_DATA_KEY

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return
        if (intent.action == MOVIE_NOTIFICATION_CHANNEL_ID) {
            val movieTitle =
                intent.getStringExtra(MOVIE_TITLE_DATA_KEY) ?: throw IllegalArgumentException()
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                createNotificationChannel(notificationManager)
                createNotification(context, movieTitle, notificationManager)
            }
        }
    }

    private fun createNotification(
        context: Context,
        movieTitle: String,
        notificationManager: NotificationManager,
    ) {
        val notification =
            NotificationCompat
                .Builder(context, MOVIE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_green_24dp)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content_text, movieTitle))
                .build()
        notificationManager.notify(movieTitle.hashCode(), notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(
                MOVIE_NOTIFICATION_CHANNEL_ID,
                MOVIE_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val MOVIE_NOTIFICATION_CHANNEL_NAME = "Movie Alarms"
    }
}
