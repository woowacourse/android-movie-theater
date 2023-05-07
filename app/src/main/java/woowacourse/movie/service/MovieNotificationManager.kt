package woowacourse.movie.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.activity.bookComplete.BookCompleteActivity
import woowacourse.movie.model.MovieBookingSeatInfoUIModel

class MovieNotificationManager(val context: Context) {

    private val notificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager

    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            PRIMARY_CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description =
            context.getString(R.string.notification_channel_description)
        this.notificationManager.createNotificationChannel(
            notificationChannel
        )
    }

    fun deliverNotification(
        movieBookingSeatInfo: MovieBookingSeatInfoUIModel,
        id: Int,
    ) {
        val contentIntent = BookCompleteActivity.getIntent(context, movieBookingSeatInfo)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            id,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = getBuilder(context, movieBookingSeatInfo, contentPendingIntent)

        this.notificationManager.notify(id, builder.build())
    }

    private fun getBuilder(
        context: Context,
        movieBookingSeatInfo: MovieBookingSeatInfoUIModel,
        contentPendingIntent: PendingIntent?
    ) = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID).apply {
        setSmallIcon(R.drawable.noti_icon_24)
        setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                movieBookingSeatInfo.movieBookingInfo.theaterMovie.movieInfo.movie.poster
            )
        )
        setContentTitle(context.getString(R.string.notification_title))
        setContentText(
            context.getString(
                R.string.notification_content,
                movieBookingSeatInfo.movieBookingInfo.theaterMovie.movieInfo.movie.title
            )
        )
        setContentIntent(contentPendingIntent)
        priority = NotificationCompat.PRIORITY_HIGH
        setAutoCancel(true)
        setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    companion object {
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}
