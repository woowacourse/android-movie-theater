package woowacourse.movie

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val title = intent.getStringExtra(EXTRA_TITLE) ?: return
        val reservationId = intent.getLongExtra(EXTRA_RESERVATION_ID, DEFAULT_NOTIFICATION_ID)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.push_reservation_title))
                .setContentText(context.getString(R.string.push_reservation_text, title))
                .setSmallIcon(R.drawable.baseline_movie)
                .build()
        notificationManager.notify(reservationId.toInt(), notification)
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val DEFAULT_NOTIFICATION_ID = 1L
        private const val EXTRA_RESERVATION_ID = "reservationId"
        private const val EXTRA_TITLE = "title"

        fun newIntent(context: Context, reservationId: Long, title: String): Intent {
            return Intent(context, NotificationReceiver::class.java).apply {
                putExtra(EXTRA_RESERVATION_ID, reservationId)
                putExtra(EXTRA_TITLE, title)
            }
        }
    }
}
