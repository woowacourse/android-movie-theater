package woowacourse.movie.view.reservation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.data.ApplicationSettings
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.view.ticket.ReservationDetailActivity

class ReservationAlarmReceiver : BroadcastReceiver() {
    private var notificationBuilder: NotificationCompat.Builder? = null

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!ApplicationSettings.notificationEnabled) return
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        createNotificationChannel(context)
        val reservation: Reservation = intent.getTicketExtra(EXTRA_RESERVATION) ?: return
        val pendingIntent = pendingIntent(context, reservation)
        getOrCreateNotificationBuilder(context, reservation, pendingIntent).notify(context)
    }

    private fun getOrCreateNotificationBuilder(
        context: Context,
        reservation: Reservation,
        pendingIntent: PendingIntent?,
    ): NotificationCompat.Builder =
        notificationBuilder ?: notificationBuilder(
            context,
            reservation,
            pendingIntent,
        ).also { notificationBuilder = it }

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

    private fun pendingIntent(
        context: Context,
        reservation: Reservation,
    ): PendingIntent? =
        PendingIntent.getActivity(
            context,
            0,
            ReservationDetailActivity.Companion.newIntent(context, reservation),
            PendingIntent.FLAG_IMMUTABLE,
        )

    private fun notificationBuilder(
        context: Context,
        reservation: Reservation,
        pendingIntent: PendingIntent?,
    ): NotificationCompat.Builder =
        NotificationCompat
            .Builder(context, context.getString(R.string.reservation_alarm_channel_id))
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(context.getString(R.string.reservation_alarm_content_title))
            .setContentText(
                context.getString(
                    R.string.reservation_alarm_content_text_format,
                    reservation.title,
                ),
            ).setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

    private fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(
                context.getString(R.string.reservation_alarm_channel_id),
                CHANNEL_NAME,
                importance,
            ).apply {
                description = context.getString(R.string.reservation_alarm_channel_description)
            }
        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun NotificationCompat.Builder.notify(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(0, build())
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            reservation: Reservation,
        ): Intent =
            Intent(context, ReservationAlarmReceiver::class.java)
                .putExtra(EXTRA_RESERVATION, reservation)

        private const val EXTRA_RESERVATION = "woowacourse.movie.EXTRA_RESERVATION"
        private const val CHANNEL_NAME = "Movie Showtime Notification"
    }
}
