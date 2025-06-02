package woowacourse.movie.view.setting.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.Ticket
import woowacourse.movie.util.getSerializableExtraCompat
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity
import woowacourse.movie.view.setting.notification.NotificationHelper.CHANNEL_ID

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val ticket = intent.getSerializableExtraCompat(TICKET_KEY, Ticket::class.java)
        val receivedIntent =
            ticket?.let { ReservationCompleteActivity.newIntent(context, it) } ?: return

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                ticket.hashCode(),
                receivedIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val builder =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle(context.getString(R.string.reservation_notification_title))
                .setContentText(
                    context.getString(
                        R.string.notification_reservation_text,
                        ticket.title,
                    ),
                ).setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ticket.hashCode(), builder.build())
    }

    companion object {
        private const val TICKET_KEY = "TICKET"
    }
}
