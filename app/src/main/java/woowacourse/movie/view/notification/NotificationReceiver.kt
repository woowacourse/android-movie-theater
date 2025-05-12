package woowacourse.movie.view.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.data.setting.SettingStorageManagerImpl
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.MainActivity.Companion.CHANNEL_ID
import woowacourse.movie.view.home.complete.BookingCompleteActivity
import woowacourse.movie.view.home.complete.BookingCompleteActivity.Companion.KEY_TICKET
import woowacourse.movie.view.util.getSerializableCompat
import woowacourse.movie.view.util.showToast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return
        val ticket: Ticket? = intent.extras?.getSerializableCompat(KEY_TICKET)
        if (ticket == null) {
            context.showToast(context.getString(R.string.text_error))
            return
        }

        val manager = SettingStorageManagerImpl(context)
        if (manager.isNotificationEnabled()) sendMovieNotification(context, ticket)
    }

    private fun sendMovieNotification(
        context: Context,
        ticket: Ticket,
    ) {
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                ticket.hashCode(),
                BookingCompleteActivity.newIntent(context, ticket, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notification: Notification =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.text_booking_notificaiton_title))
                .setContentText(
                    context.getString(
                        R.string.text_booking_notification_body,
                        ticket.movieTitle,
                    ),
                )
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_planet)
                .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ticket.hashCode(), notification)
    }

    companion object {
        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra(KEY_TICKET, ticket)
            }
    }
}
