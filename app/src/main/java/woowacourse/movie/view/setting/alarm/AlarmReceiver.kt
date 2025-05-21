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
import woowacourse.movie.TicketProvider
import woowacourse.movie.data.NotificationRepository
import woowacourse.movie.domain.movieseat.RemainTimePolicy
import woowacourse.movie.view.reservation.TicketUi
import woowacourse.movie.view.reservation.result.ReservationCompleteActivity

class AlarmReceiver(
    private val repository: NotificationRepository = TicketProvider.notificationRepository,
) : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!repository.getNotificationEnabled()) {
            return
        }

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
                context.getString(R.string.notification_reserve),
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description =
                    context.getString(R.string.notification_reservation_movie_start)
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
                .setContentTitle(context.getString(R.string.notification_reserve))
                .setContentText(
                    context.getString(
                        R.string.notificaton_reservation_movie_alarm,
                        ticketUi.title,
                        RemainTimePolicy.NormalPolicy.time.minute,
                    ),
                )
                .setContentIntent(pendingIntent)

        notificationManager.notify(1, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "alarm_channel"
        private const val KEY_TICKET = "ticket"
    }
}
