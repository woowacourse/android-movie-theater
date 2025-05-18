package woowacourse.movie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
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
        val ticket = extractTicket(intent)

        ensureNotificationChannel(context)
        showNotification(context, ticket)
    }

    private fun isPermitted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun extractTicket(intent: Intent): TicketUiModel {
        return IntentCompat.getParcelableExtra(intent, KEY_TICKET_ALARM, TicketUiModel::class.java)
    }

    private fun ensureNotificationChannel(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(
        context: Context,
        ticket: TicketUiModel,
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = BookingCompleteActivity.newIntent(context, ticket)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                CHANNEL_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content_text, ticket.title))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(ticket.hashCode(), notification)
    }

    companion object {
        private const val CHANNEL_REQUEST_CODE = 0
        private const val CHANNEL_ID = "ALARM_CHANNEL"
        private const val CHANNEL_NAME = "TICKET_NOTIFICATION"
        private const val KEY_TICKET_ALARM = "TICKET_ALARM_DATA"

        private fun newIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_TICKET_ALARM, ticket)
            }

        fun newPendingIntent(
            context: Context,
            ticket: TicketUiModel,
        ): PendingIntent {
            return PendingIntent.getBroadcast(context, 0, newIntent(context, ticket), PendingIntent.FLAG_IMMUTABLE)
        }
    }
}
