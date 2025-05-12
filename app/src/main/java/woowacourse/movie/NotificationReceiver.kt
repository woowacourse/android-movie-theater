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
        val reservationId = intent.getLongExtra("reservationId", -1)

        if (!isNotificationEnabled) {
            return
        }

        val notifyIntent = MovieBookedActivity.newIntent(context, reservationId)

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification =
            NotificationCompat.Builder(context, "channel_id")
                .setContentTitle("예매 알림")
                .setContentText("$title 30분 후 상영 예정")
                .setSmallIcon(R.drawable.baseline_movie)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(1, notification)
    }

    private fun showNotification(context: Context) {
    }
}
