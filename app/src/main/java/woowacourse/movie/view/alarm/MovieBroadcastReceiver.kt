package woowacourse.movie.view.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity

class MovieBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (intent?.action == "android.intent.action.ALARM_ACTION") {
            val reservationId: Long = intent.getLongExtra(RESERVATION_ID_KEY, 0L)
            val movieTitle: String = intent.getStringExtra(MOVIE_TITLE_KEY).orEmpty()

            sendMovieNotification(context ?: return, reservationId, movieTitle)
        }
    }

    private fun sendMovieNotification(
        context: Context,
        reservationId: Long,
        movieTitle: String,
    ) {
        createNotificationChannel(context)
        val pendingIntent: PendingIntent = createPendingIntent(context, reservationId)
        val notification: Notification =
            showNotification(context, movieTitle, pendingIntent)
        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(
                "WTC",
                "MOVIE",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        NotificationManagerCompat
            .from(context)
            .createNotificationChannel(channel)
    }

    private fun createPendingIntent(
        context: Context,
        reservationId: Long,
    ): PendingIntent {
        val mainIntent = MainActivity.getIntent(context)
        val reservationIntent = ReservationCompleteActivity.getIntent(context, reservationId)

        return TaskStackBuilder.create(context).run {
            addNextIntent(mainIntent)
            addNextIntentWithParentStack(reservationIntent)
            getPendingIntent(
                reservationId.toInt(),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        }
    }

    private fun showNotification(
        context: Context,
        movieTitle: String,
        pendingIntent: PendingIntent,
    ): Notification =
        NotificationCompat
            .Builder(context, "WTC")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("예매 알림")
            .setContentText("$movieTitle 30분 후에 상영")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

    companion object {
        const val RESERVATION_ID_KEY = "reservation_id"
        const val MOVIE_TITLE_KEY = "movie_title"
    }
}
