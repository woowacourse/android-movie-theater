package woowacourse.movie.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.view.main.home.bookcomplete.BookCompleteActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val activityIntent = Intent(
            context, BookCompleteActivity::class.java
        ).putExtra(
            BookCompleteActivity.BOOKING_COMPLETE_INFO_INTENT_KEY,
            intent!!.getLongExtra(
                BookCompleteActivity.BOOKING_COMPLETE_INFO_INTENT_KEY,
                -1L
            ),
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context!!, 0, activityIntent, FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val name = "getString(R.string.channel_name)"
        val descriptionText = "getString(R.string.channel_description)"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(1, builder.build())
    }
}
