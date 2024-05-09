package woowacourse.movie.ui.setting.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.ui.setting.notification.MovieAlarmManager.REQUEST_CODE

class MovieAlarmNotificationBuilder(
    private val context: Context,
    private val movieTitle: String,
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

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        builder.apply {
            setSmallIcon(R.drawable.thumbnail_movie2)
            setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.thumbnail_movie2,
                ),
            )
            setContentTitle("예매 알림")
        }

        return manager
    }

    private fun notificationBuilder(
        context: Context,
        movieTitle: String,
        movieIntent: Intent,
    ): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        val pendingIntent =
            PendingIntent.getActivity(context, REQUEST_CODE, movieIntent, PendingIntent.FLAG_IMMUTABLE)

        builder.apply {
            setSmallIcon(R.drawable.thumbnail_movie2)
            setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.thumbnail_movie2,
                ),
            )
            setContentTitle("예매 알림")
            setContentText(movieTitle)
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        return builder
    }

    private fun notificationChannel(): NotificationChannel {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "My Little Description"
            }
        return channel
    }

    companion object {
        private const val CHANNEL_ID = "one-channel"
        private const val CHANNEL_NAME = "Channel One"
        private const val NOTIFICATION_ID = 11
    }
}
