package woowacourse.movie.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

class MovieAlarmNotificationBuilder(
    private val context: Context,
    private val movieTitle: String,
    private val requestCode: Int,
    private val movieIntent: Intent,
) {
    private val builder by lazy { notificationBuilder(context, movieTitle, movieIntent) }
    private val manager by lazy { notificationManager(context) }

    fun notifyAlarm() {
        manager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun notificationManager(context: Context): NotificationManager {
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel())

        return manager
    }

    private fun notificationBuilder(
        context: Context,
        movieTitle: String,
        movieIntent: Intent,
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        val pendingIntent = makePendingIntent(context, movieIntent)

        builder.apply {
            setSmallIcon(R.drawable.thumbnail_movie2)
            setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.thumbnail_movie2,
                ),
            )
            setContentTitle(context.getString(R.string.movie_alarm_title))
            setContentText(context.getString(R.string.movie_alarm_comment, movieTitle))
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        return builder
    }

    private fun makePendingIntent(
        context: Context,
        movieIntent: Intent,
    ): PendingIntent? = PendingIntent.getActivity(context, requestCode, movieIntent, PendingIntent.FLAG_IMMUTABLE)

    private fun notificationChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
    }

    companion object {
        private const val CHANNEL_ID = "one-channel"
        private const val CHANNEL_NAME = "Channel One"
        private const val NOTIFICATION_ID = 11
    }
}
