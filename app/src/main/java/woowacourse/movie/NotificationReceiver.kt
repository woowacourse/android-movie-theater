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
        val sharedPref = context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPref.getBoolean(KEY_NOTIFICATION, true)

        if (!isNotificationEnabled) return

        val title = intent.getStringExtra(EXTRA_TITLE) ?: return
        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, DEFAULT_NOTIFICATION_ID)

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
        private const val EXTRA_RESERVATION_ID = "reservationId"
        private const val EXTRA_TITLE = "title"
        private const val KEY_SETTINGS = "settings"
        private const val KEY_NOTIFICATION = "notification"

        fun newIntent(context: Context, reservationId: Long, title: String): Intent {
            return Intent(context, NotificationReceiver::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
                putExtra(EXTRA_TITLE, title)
            }
        }
    }
}
