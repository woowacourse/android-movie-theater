package woowacourse.movie

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.ui.model.TicketUiModel

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!isPermitted(context)) return

        val ticket = IntentCompat.getParcelableExtra(intent, KEY_TICKET_ALARM, TicketUiModel::class.java)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

        val channel =
            android.app.NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_HIGH,
            )

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            notificationManager.createNotificationChannel(channel)
        }

        val bookIntent = BookingCompleteActivity.newIntent(context, ticket)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                CHANNEL_REQUEST_CODE,
                bookIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            androidx.core.app.NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content_text, ticket.title))
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(ticket.hashCode(), notification)
    }

    private fun isPermitted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val CHANNEL_REQUEST_CODE = 0
        private const val CHANNEL_ID = "ALARM_CHANNEL"
        private const val CHANNEL_NAME = "TICKET_NOTIFICATION"
        private const val KEY_TICKET_ALARM = "TICKET_ALARM_DATA"

        fun newIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_TICKET_ALARM, ticket)
            }
    }
}
