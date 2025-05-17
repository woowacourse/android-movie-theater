package woowacourse.movie

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.moviebooked.MovieBookedActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPref.getBoolean("notification", true)

        val title = intent.getStringExtra("title") ?: return
        val reservationId = intent.getLongExtra("reservationId", DEFAULT_NOTIFICATION_ID)

        if (!isNotificationEnabled) return

        val notifyIntent = MovieBookedActivity.newIntent(context, reservationId)

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                reservationId.toInt(),
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.push_reservation_title))
                .setContentText(context.getString(R.string.push_reservation_text, title))
                .setSmallIcon(R.drawable.baseline_movie)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(reservationId.toInt(), notification)
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val DEFAULT_NOTIFICATION_ID = 1L
    }
}
