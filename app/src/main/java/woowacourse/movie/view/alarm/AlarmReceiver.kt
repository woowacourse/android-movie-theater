package woowacourse.movie.view.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.view.alarm.AlarmFactory.Companion.MOVIE_ALARM_ACTION
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity.Companion.TICKET_DATA_KEY

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return
        if (intent.action == MOVIE_ALARM_ACTION) {
            val movieTicket =
                intent.getSerializableExtraData<MovieTicket>(TICKET_DATA_KEY) ?: return
            val pendingIntent =
                PendingIntent.getActivity(
                    context,
                    0,
                    ReservationCompleteActivity.getIntent(context, movieTicket),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                createNotificationChannel(notificationManager)
                createNotification(context, movieTicket.title, notificationManager, pendingIntent)
            }
        }
    }

    private fun createNotification(
        context: Context,
        movieTitle: String,
        notificationManager: NotificationManager,
        pendingIntent: PendingIntent,
    ) {
        val notification =
            NotificationCompat
                .Builder(context, MOVIE_ALARM_ACTION)
                .setSmallIcon(R.drawable.ic_android_green_24dp)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content_text, movieTitle))
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(movieTitle.hashCode(), notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(
                MOVIE_ALARM_ACTION,
                MOVIE_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val MOVIE_NOTIFICATION_CHANNEL_NAME = "Movie Alarms"
    }
}
