package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.activity.BookCompleteActivity
import woowacourse.movie.movie.MovieBookingSeatInfo

class MovieReminder : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val canPush = getPushAlarmAllowed(context)

        if (!canPush) return

        val movieBookingSeatInfo =
            intent.getSerializableCompat<MovieBookingSeatInfo>(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY) ?: return
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel(context)
        deliverNotification(context, movieBookingSeatInfo)
    }

    private fun getPushAlarmAllowed(context: Context) =
        SharedPreferenceUtil(context).getSettingValue(
            BundleKeys.SETTING_PUSH_ALARM_SWITCH_KEY,
            false
        )

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

    private fun deliverNotification(context: Context, movieBookingSeatInfo: MovieBookingSeatInfo) {
        val contentIntent = BookCompleteActivity.intent(context, movieBookingSeatInfo)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val builder = getBuilder(context, movieBookingSeatInfo, contentPendingIntent)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun getBuilder(
        context: Context,
        movieBookingSeatInfo: MovieBookingSeatInfo,
        contentPendingIntent: PendingIntent?
    ) = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID).apply {
        setSmallIcon(R.drawable.noti_icon_24)
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
