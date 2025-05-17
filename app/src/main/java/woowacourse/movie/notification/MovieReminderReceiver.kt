package woowacourse.movie.notification

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
import woowacourse.movie.R
import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.ui.complete.BookingCompleteActivity
import woowacourse.movie.utils.intentSerializable

class MovieReminderReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (hasPostNotificationPermission(context)) {
            val bookedTicket =
                intent.intentSerializable(EXTRA_BOOKED_TICKET, BookedTicket::class.java)!!
            showNotification(context, bookedTicket)
        }
    }

    private fun hasPostNotificationPermission(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED
            )
        } else {
            true
        }

    private fun showNotification(
        context: Context,
        bookedTicket: BookedTicket,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        val intent = BookingCompleteActivity.newIntent(context, bookedTicket.id!!)
        val requestCode = bookedTicket.id.toInt()
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(
                    context.getString(R.string.notification_message, bookedTicket.movieTitle),
                )
                .setSmallIcon(R.drawable.woowacourse)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        notificationManager.notify(requestCode, notification)
    }

    companion object {
        private const val EXTRA_BOOKED_TICKET = "EXTRA_BOOKED_TICKET"
        private const val CHANNEL_ID = "MOVIE_NOTIFICATION_CHANNEL"
        private const val CHANNEL_NAME = "MOVIE_NOTIFICATION"

        fun newIntent(
            context: Context,
            bookedTicket: BookedTicket,
        ): Intent {
            return Intent(context, MovieReminderReceiver::class.java).apply {
                putExtra(EXTRA_BOOKED_TICKET, bookedTicket)
            }
        }
    }
}
