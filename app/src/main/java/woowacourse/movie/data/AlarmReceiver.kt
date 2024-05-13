package woowacourse.movie.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.ticket.MovieTicketActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val movieTicketId = intent.getLongExtra(EXTRA_MOVIE_TICKET_ID, INVALID_ID)
        if (movieTicketId > INVALID_ID) {
            sendNotification(context, movieTicketId)
        }
    }

    private fun sendNotification(context: Context, movieTicketId: Long) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = movieTicketId.toInt()
        val channelId = "movie_notification_channel"
        val channelName = "Movie Notification Channel"

        // 채널 생성 (Android 8.0 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notification channel for movie alerts"
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, MovieTicketActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_MOVIE_TICKET_ID, movieTicketId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_content))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        const val INVALID_ID = -1L
        const val EXTRA_MOVIE_TICKET_ID = "movie_ticket_Id"
    }
}
