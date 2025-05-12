package woowacourse.movie

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.view.ticket.ReservationDetailActivity

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservation: Reservation = intent.getTicketExtra(EXTRA_RESERVATION) ?: return
        val intent = ReservationDetailActivity.newIntent(context, reservation)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder: NotificationCompat.Builder =
            notificationBuilder(context, reservation, pendingIntent)

        notify(context, builder)
    }

    private fun notify(
        context: Context,
        builder: NotificationCompat.Builder,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    private fun notificationBuilder(
        context: Context,
        reservation: Reservation,
        pendingIntent: PendingIntent?,
    ): NotificationCompat.Builder =
        NotificationCompat
            .Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.drawable.home_icon)
            .setContentTitle("예매 알림")
            .setContentText("${reservation.title} 30분 후에 상영")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

    @Suppress("DEPRECATION")
    private fun Intent.getTicketExtra(key: String): Reservation? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                getSerializableExtra(
                    key,
                    Reservation::class.java,
                )

            else -> getSerializableExtra(key) as? Reservation
        }

    companion object {
        fun newIntent(
            context: Context,
            reservation: Reservation,
        ): Intent =
            Intent(context, ReservationAlarmReceiver::class.java)
                .putExtra(EXTRA_RESERVATION, reservation)

        private const val EXTRA_RESERVATION = "woowacourse.movie.EXTRA_RESERVATION"
    }
}
