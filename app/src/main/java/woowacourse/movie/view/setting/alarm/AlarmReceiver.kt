package woowacourse.movie.view.setting.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.view.reservation.TicketUi
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity

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

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "예매 알림",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description =
                    "예매한 영화의 시작 알림"
            }

        notificationManager.createNotificationChannel(channel)

        val ticketUi =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(KEY_TICKET, TicketUi::class.java)
            } else {
                intent.getSerializableExtra(KEY_TICKET) as? TicketUi
            }

        val receivedIntent = ticketUi?.let { ReservationCompleteActivity.newIntent(context, it) } ?: return

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                context,
                ticketUi.hashCode(),
                receivedIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )

        val builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("예매 알림")
                .setContentText("${ticketUi.title} 30분 후 상영")
                .setContentIntent(pendingIntent)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "alarm_channel"
        private const val KEY_TICKET = "ticket"
    }
}
