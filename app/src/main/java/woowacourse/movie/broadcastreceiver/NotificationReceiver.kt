package woowacourse.movie.broadcastreceiver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.getParcelable
import woowacourse.movie.ui.model.MovieTicketModel

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val ticketModel =
            intent.getParcelable<MovieTicketModel>(TICKET_EXTRA_KEY) ?: return

        createNotificationChannel(context)

        notifyReservation(context, ticketModel)
    }

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.reservation_notification_title)
        val descriptionText =
            context.getString(R.string.reservation_notification_channel_description)
        val channel =
            NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun notifyReservation(
        context: Context,
        ticketModel: MovieTicketModel
    ) {
        val ticketIntent = MovieTicketActivity.createIntent(context, ticketModel)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                ticketModel.hashCode(),
                ticketIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        val notificationBuilder = getNotificationBuilder(context, ticketModel, pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (checkPermission(context)) notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun getNotificationBuilder(
        context: Context,
        ticketModel: MovieTicketModel,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_reservation_notification)
            setContentTitle(context.getString(R.string.reservation_notification_title))
            setContentText(
                context.getString(
                    R.string.reservation_notification_content,
                    ticketModel.title
                )
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        return notificationBuilder
    }

    private fun checkPermission(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    companion object {
        private const val CHANNEL_ID = "reservation"
        private const val NOTIFICATION_ID = 1
        private const val TICKET_EXTRA_KEY = "ticket"

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
