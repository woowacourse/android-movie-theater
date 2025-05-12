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
        val reservation: Reservation = intent.getTicketExtra(EXTRA_RESERVATION) ?: return
        val pendingIntent = pendingIntent(context, reservation)
        val builder: NotificationCompat.Builder =
            notificationBuilder(context, reservation, pendingIntent)
        createNotificationChannel(context)
        builder.notify(context)
    }

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
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(CONTENT_TITLE)
            .setContentText(CONTENT_TEXT_FORMAT.format(reservation.title))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

    private fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
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
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                // ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                //                                        grantResults: IntArray)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return@with
            }
            // notificationId is a unique int for each notification that you must define.
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
        private const val CHANNEL_ID = "상영 시각 30분 전 알림 채널"
        private const val CONTENT_TITLE = "예매 알림"
        private const val CONTENT_TEXT_FORMAT = "%s 30분 후에 상영"
        private const val CHANNEL_NAME = "Movie Showtime Notification"
        private const val CHANNEL_DESCRIPTION = "영화 시작 30분 전 알림을 발송합니다."
    }
}
