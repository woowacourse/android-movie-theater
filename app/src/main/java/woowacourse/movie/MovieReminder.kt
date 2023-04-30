package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.app.NotificationCompat
import woowacourse.movie.activity.BookCompleteActivity
import woowacourse.movie.movie.MovieBookingSeatInfoUIModel

class MovieReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val canPush = getPushAlarmAllowed(context)

        if (!canPush) return

        val movieBookingSeatInfo =
            intent.getSerializableCompat<MovieBookingSeatInfoUIModel>(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY)
                ?: return
        val notificationManager = MovieNotificationManager(context)

        val id = intent.getIntExtra(BundleKeys.ALARM_NOTIFICATION_ID, 0)
        createNotificationChannel(context, notificationManager)
        deliverNotification(context, movieBookingSeatInfo, id, notificationManager)
    }

    private fun getPushAlarmAllowed(context: Context) =
        SharedPreferenceUtil(context).getSettingValue(
            BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY,
            false
        )

    private fun createNotificationChannel(
        context: Context,
        notificationManager: MovieNotificationManager
    ) {
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
        notificationManager.createChannel(notificationChannel)
    }

    private fun deliverNotification(
        context: Context,
        movieBookingSeatInfo: MovieBookingSeatInfoUIModel,
        id: Int,
        notificationManager: MovieNotificationManager
    ) {
        val contentIntent = BookCompleteActivity.getIntent(context, movieBookingSeatInfo)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            id,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val builder = getBuilder(context, movieBookingSeatInfo, contentPendingIntent)

        notificationManager.notify(id, builder)
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
                movieBookingSeatInfo.movieBookingInfo.movieInfo.poster
            )
        )
        setContentTitle(context.getString(R.string.notification_title))
        setContentText(
            context.getString(
                R.string.notification_content,
                movieBookingSeatInfo.movieBookingInfo.movieInfo.title
            )
        )
        setContentIntent(contentPendingIntent)
        priority = NotificationCompat.PRIORITY_HIGH
        setAutoCancel(true)
        setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    companion object {
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}
