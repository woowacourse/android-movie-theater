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
        val canPush = SharedPreferenceUtil(context).getSettingValue("switch", false)
        if (!canPush) return

        val movieBookingSeatInfo =
            intent.getSerializableExtra(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY) as MovieBookingSeatInfo
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        createNotificationChannel()
        deliverNotification(context, movieBookingSeatInfo)
    }

    private fun deliverNotification(context: Context, movieBookingSeatInfo: MovieBookingSeatInfo) {
        val contentIntent = Intent(context, BookCompleteActivity::class.java)
        contentIntent.putExtra(BundleKeys.MOVIE_BOOKING_SEAT_INFO_KEY, movieBookingSeatInfo)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_MUTABLE
        )
        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.noti_icon_24)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(
                    context.getString(
                        R.string.notification_content,
                        movieBookingSeatInfo.movieBookingInfo.movieInfo.title
                    )
                )
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

    companion object {
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}
