package woowacourse.movie.feature.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.data.setting.MovieReminderSetting
import woowacourse.movie.model.TicketNotificationState
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableCompat

class MovieReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val movieReminderSetting: MovieReminderSetting = MovieReminderSetting.getInstance().init(context)
        val tickets: TicketsState = intent?.extras?.getParcelableCompat(KEY_TICKETS) ?: return

        if (movieReminderSetting.isEnable) {
            val ticketNotificationState = TicketNotificationState(
                contentTitle = context.getString(R.string.alarm_receiver_notification_title),
                contentText = context.getString(R.string.alarm_receiver_notification_text).format(
                    tickets.movie.title
                ),
                cancelable = true
            )
            sendNotification(context, ticketNotificationState)
        }

        movieReminderSetting.releaseInstance()
    }

    /**
     * return values
     * NOTIFICATION_FAIL_NO_PERMISSION = -1
     */
    private fun sendNotification(context: Context, ticketNotificationState: TicketNotificationState): Int {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return NOTIFICATION_FAIL_NO_PERMISSION
        }

        val notification = createNotification(context, ticketNotificationState)
        val notificationId = notifyId++
        NotificationManagerCompat.from(context).notify(notificationId, notification)
        return notificationId
    }

    private fun createNotification(context: Context, ticketNotificationState: TicketNotificationState): Notification {
        createNotificationChannel(context)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(ticketNotificationState.iconResId)
            .setContentTitle(ticketNotificationState.contentTitle)
            .setContentText(ticketNotificationState.contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(ticketNotificationState.cancelable)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        val id = CHANNEL_ID
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance).apply {
            description = descriptionText
            setShowBadge(true)
            setBypassDnd(true)
            lightColor = Color.RED
            enableLights(true)
            lockscreenVisibility = Notification.VISIBILITY_SECRET
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "movie_channel"
        private const val CHANNEL_NAME = "movie_channel_name"
        private const val CHANNEL_DESCRIPTION = "This is a movie channel"
        const val NOTIFICATION_FAIL_NO_PERMISSION = -1
        private var notifyId: Int = 0

        const val KEY_TICKETS = "key_tickets"
    }
}
